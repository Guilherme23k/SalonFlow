import {
  Body,
  Headers,
  Controller,
  Post,
  UseInterceptors,
  Patch,
  Param,
} from '@nestjs/common';
import { TenantInterceptor } from '../../common/interceptors/tenant.interceptor';
import { AppointmentService } from './appointment.service';
import { CreateAppointmentDto } from './dto/create-appointment.dto';

@Controller('api/v1/appointments')
@UseInterceptors(TenantInterceptor)
export class AppointmentController {
  constructor(private readonly appointmentService: AppointmentService) {}

  @Post()
  async create(
    @Headers('x-tenant-id') tenantId: string,
    @Body() dto: CreateAppointmentDto,
  ) {
    return this.appointmentService.create(tenantId, dto);
  }

  @Patch(':id/cancel')
  async cancel(
    @Headers('x-tenant-id') tenantId: string,
    @Param('id') id: string,
  ) {
    return this.appointmentService.cancel(tenantId, id);
  }
}
