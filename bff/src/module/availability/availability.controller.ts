import {
  Controller,
  Get,
  Headers,
  Query,
  UseInterceptors,
} from '@nestjs/common';
import { TenantInterceptor } from '../../common/interceptors/tenant.interceptor';
import { AvailabilityService } from './availability.service';
import { GetAvailabilityDTO } from './dto/get-availability.dto';
import { ApiHeader, ApiOperation, ApiQuery, ApiTags } from '@nestjs/swagger';

@ApiTags('Availability')
@ApiHeader({
  name: 'x-tenant-id',
  required: true,
  description: 'UUID do tenant',
})
@Controller('api/v1/availability')
@UseInterceptors(TenantInterceptor)
export class AvailabilityController {
  constructor(private readonly service: AvailabilityService) {}

  @Get()
  @ApiOperation({
    summary: 'Lista slots disponíveis de um profissional em uma data',
  })
  @ApiQuery({
    name: 'professionalId',
    required: true,
    description: 'UUID do profissional',
  })
  @ApiQuery({
    name: 'serviceId',
    required: true,
    description: 'UUID do serviço',
  })
  @ApiQuery({
    name: 'date',
    required: true,
    description: 'Data no formato YYYY-MM-DD',
  })
  async getAvailability(
    @Headers('x-tenant-id') tenantId: string,
    @Query() dto: GetAvailabilityDTO,
  ) {
    return this.service.getAvailableSlots(tenantId, dto);
  }
}
