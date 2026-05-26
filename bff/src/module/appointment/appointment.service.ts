import { Injectable } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';
import { CreateAppointmentDto } from './dto/create-appointment.dto';

@Injectable()
export class AppointmentService {
  constructor(private readonly springClient: SpringClientService) {}

  async create(tenantId: string, dto: CreateAppointmentDto) {
    return this.springClient.post('/api/v1/appointments', tenantId, dto);
  }

  async cancel(tenantId: string, id: string) {
    return this.springClient.patch(
      `/api/v1/appointments/${id}/cancel`,
      tenantId,
      {},
    );
  }
}
