import { Controller, Get, Headers, UseInterceptors } from '@nestjs/common';
import { TenantInterceptor } from '../../common/interceptors/tenant.interceptor';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { ProfessionalService } from './professional.service';

@ApiTags('Professionals')
@UseInterceptors(TenantInterceptor)
@Controller('api/v1/professionals')
export class ProfessionalController {
  constructor(private readonly professionalService: ProfessionalService) {}

  @Get()
  @ApiOperation({ summary: 'list of all actives professionals on tenant' })
  async findAll(@Headers('x-tenant-id') tenantId: string) {
    return this.professionalService.findAll(tenantId);
  }
}
