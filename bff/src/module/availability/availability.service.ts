// src/module/availability/availability.service.ts
import { Injectable, NotFoundException } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';
import {
  TenantResponse,
  ServiceDurationResponse,
  AppointmentResponse,
  BlockedSlotResponse,
} from './interfaces/spring-responses.interface';
import { GetAvailabilityDTO } from './dto/get-availability.dto';
import { AvailableSlot } from './interfaces/available-slot';

const BUFFER_MINUTES = 5;
const SLOT_INTERVAL_MINUTES = 30;

@Injectable()
export class AvailabilityService {
  constructor(private readonly springClient: SpringClientService) {}

  async getAvailableSlots(
    tenantId: string,
    dto: GetAvailabilityDTO,
  ): Promise<AvailableSlot[]> {
    const [tenant, serviceDuration, appointments, blockedSlots] =
      await Promise.all([
        this.springClient.get<TenantResponse>(
          `/api/v1/tenants/${tenantId}`,
          tenantId,
        ),
        this.springClient.get<ServiceDurationResponse>(
          `/api/v1/service-durations/professional/${dto.professionalId}/service/${dto.serviceId}`,
          tenantId,
        ),
        this.springClient.get<AppointmentResponse[]>(
          `/api/v1/appointments/professional/${dto.professionalId}?date=${dto.date}`,
          tenantId,
        ),
        this.springClient.get<BlockedSlotResponse[]>(
          `/api/v1/blocked-slots/professional/${dto.professionalId}?date=${dto.date}`,
          tenantId,
        ),
      ]);

    if (!serviceDuration) {
      throw new NotFoundException(
        'Este profissional não realiza o serviço solicitado',
      );
    }

    const slots = this.generateSlots(
      dto.date,
      tenant.openingTime,
      tenant.closingTime,
    );

    return slots.map((slotTime) => ({
      time: slotTime,
      available: this.isSlotAvailable(
        slotTime,
        dto.date,
        serviceDuration.durationMinutes,
        appointments,
        blockedSlots,
        tenant.closingTime,
      ),
    }));
  }

  private generateSlots(
    date: string,
    openingTime: string,
    closingTime: string,
  ): string[] {
    const slots: string[] = [];

    const [openHour, openMin] = openingTime.split(':').map(Number);
    const [closeHour, closeMin] = closingTime.split(':').map(Number);

    const openingMinutes = openHour * 60 + openMin;
    const closingMinutes = closeHour * 60 + closeMin;

    for (
      let minutes = openingMinutes;
      minutes < closingMinutes;
      minutes += SLOT_INTERVAL_MINUTES
    ) {
      const hour = Math.floor(minutes / 60)
        .toString()
        .padStart(2, '0');
      const min = (minutes % 60).toString().padStart(2, '0');
      slots.push(`${hour}:${min}`);
    }

    return slots;
  }

  private isSlotAvailable(
    slotTime: string,
    date: string,
    durationMinutes: number,
    appointments: AppointmentResponse[],
    blockedSlots: BlockedSlotResponse[],
    closingTime: string,
  ): boolean {
    const slotStart = new Date(`${date}T${slotTime}:00`);
    const slotEnd = new Date(
      slotStart.getTime() + (durationMinutes + BUFFER_MINUTES) * 60 * 1000,
    );

    const [closeHour, closeMin] = closingTime.split(':').map(Number);
    const closing = new Date(`${date}T${closingTime}:00`);
    closing.setHours(closeHour, closeMin, 0, 0);

    if (slotEnd > closing) return false;

    for (const appointment of appointments) {
      const apptStart = new Date(appointment.scheduleAt);
      const apptEnd = new Date(
        apptStart.getTime() +
          (appointment.durationMinutes + BUFFER_MINUTES) * 60 * 1000,
      );

      if (slotStart < apptEnd && slotEnd > apptStart) return false;
    }

    // Conflito com blocked slot
    for (const blocked of blockedSlots) {
      const blockedStart = new Date(blocked.startAt);
      const blockedEnd = new Date(blocked.endAt);

      if (slotStart < blockedEnd && slotEnd > blockedStart) return false;
    }

    return true;
  }
}
