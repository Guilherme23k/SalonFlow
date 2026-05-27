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
import { ApiHeader, ApiOperation, ApiTags } from '@nestjs/swagger';

@ApiTags('Appointments')
@ApiHeader({
  name: 'x-tenant-id',
  required: true,
  description: 'UUID do tenant',
})
@Controller('api/v1/appointments')
@UseInterceptors(TenantInterceptor)
export class AppointmentController {
  constructor(private readonly appointmentService: AppointmentService) {}

  @Post()
  @ApiOperation({ summary: 'Create new appointment' })
  async create(
    @Headers('x-tenant-id') tenantId: string,
    @Body() dto: CreateAppointmentDto,
  ) {
    return this.appointmentService.create(tenantId, dto);
  }

  @Patch(':id/cancel')
  @ApiOperation({ summary: 'Cancel a appointment already existed' })
  async cancel(
    @Headers('x-tenant-id') tenantId: string,
    @Param('id') id: string,
  ) {
    return this.appointmentService.cancel(tenantId, id);
  }
}
