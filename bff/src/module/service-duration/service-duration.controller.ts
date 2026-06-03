import {
  Controller,
  Get,
  Headers,
  Param,
  UseInterceptors,
} from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { TenantInterceptor } from '../../common/interceptors/tenant.interceptor';
import { ServiceDurationService } from './service-duration.service';

@ApiTags('Service Durations')
@Controller('/api/v1/service-durations')
@UseInterceptors(TenantInterceptor)
export class ServiceDurationController {
  constructor(private readonly service: ServiceDurationService) {}

  @Get('professional/:professionalId/services')
  @ApiOperation({ summary: 'List all active services of a professional' })
  async findAllServicesByProfessionalId(
    @Headers('x-tenant-id') tenantId: string,
    @Param('professionalId') professionalId: string,
  ) {
    return this.service.findAllServicesByProfessional(tenantId, professionalId);
  }
}
