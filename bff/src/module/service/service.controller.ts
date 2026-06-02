import { Controller, Get, Headers, UseInterceptors } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { TenantInterceptor } from '../../common/interceptors/tenant.interceptor';
import { ServiceService } from './service.service';

@ApiTags('Services')
@UseInterceptors(TenantInterceptor)
@Controller('api/v1/services')
export class ServiceController {
  constructor(private readonly salonService: ServiceService) {}

  @Get()
  @ApiOperation({ summary: 'List all active services of tenant' })
  async findAll(@Headers('x-tenant-id') tenantId: string) {
    return this.salonService.findAll(tenantId);
  }

  @Get()
  @ApiOperation({ summary: 'List all active services of a professional' })
  async findAllServicesByProfessional(
    @Headers('x-tenant-id') tenantId: string,
    professionalid: string,
  ) {
    return this.salonService.findAllServicesByProfessional(
      tenantId,
      professionalid,
    );
  }
}
