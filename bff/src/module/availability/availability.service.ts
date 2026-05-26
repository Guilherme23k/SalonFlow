// src/module/availability/availability.service.ts
import { Injectable, NotFoundException } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';
import { AvailableSlot } from './interfaces/available-slot';
import {
  TenantResponse,
  ServiceDurationResponse,
  AppointmentResponse,
  BlockedSlotResponse,
} from './interfaces/spring-responses.interface';
import { GetAvailabilityDTO } from './dto/get-availability.dto';

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

    // Normaliza openingTime e closingTime — remove segundos se vier "09:00:00"
    const openingTime = tenant.openingTime.substring(0, 5); // "09:00"
    const closingTime = tenant.closingTime.substring(0, 5); // "19:00"

    const slots = this.generateSlots(openingTime, closingTime);

    return slots.map((slotTime) => ({
      time: slotTime,
      available: this.isSlotAvailable(
        slotTime,
        dto.date,
        serviceDuration.durationMinutes,
        appointments ?? [],
        blockedSlots ?? [],
        closingTime,
      ),
    }));
  }

  private generateSlots(openingTime: string, closingTime: string): string[] {
    const slots: string[] = [];
    const openingMinutes = this.timeToMinutes(openingTime);
    const closingMinutes = this.timeToMinutes(closingTime);

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
    const slotStartMinutes = this.timeToMinutes(slotTime);
    const slotEndMinutes = slotStartMinutes + durationMinutes + BUFFER_MINUTES;

    // Slot ultrapassa o horário de fechamento
    const closingMinutes = this.timeToMinutes(closingTime);
    if (slotEndMinutes > closingMinutes) return false;

    // Conflito com agendamentos existentes
    for (const appointment of appointments) {
      const apptTime = appointment.scheduledAt.substring(11, 16); // "09:00"
      const apptStartMinutes = this.timeToMinutes(apptTime);
      const apptEndMinutes =
        apptStartMinutes + appointment.durationMinutes + BUFFER_MINUTES;

      if (
        slotStartMinutes < apptEndMinutes &&
        slotEndMinutes > apptStartMinutes
      ) {
        return false;
      }
    }

    // Conflito com blocked slots
    for (const blocked of blockedSlots) {
      const blockedDate = blocked.startAt.substring(0, 10);
      if (blockedDate !== date) continue;

      const blockedStartMinutes = this.timeToMinutes(
        blocked.startAt.substring(11, 16),
      );
      const blockedEndMinutes = this.timeToMinutes(
        blocked.endAt.substring(11, 16),
      );

      if (
        slotStartMinutes < blockedEndMinutes &&
        slotEndMinutes > blockedStartMinutes
      ) {
        return false;
      }
    }

    return true;
  }

  private timeToMinutes(time: string): number {
    const [hour, min] = time.split(':').map(Number);
    return hour * 60 + min;
  }
}
