import { Controller, Get, Param } from '@nestjs/common';
import { ApiOperation, ApiParam, ApiTags } from '@nestjs/swagger';
import { TenantService } from './tenant.service';

@ApiTags('Tenant')
@Controller('api/v1/tenants')
export class TenantController {
  constructor(private readonly tenantService: TenantService) {}

  @Get('slug/:slug')
  @ApiOperation({
    summary: 'Search tenant by slug - used by frontend to solve the tenant url',
  })
  @ApiParam({
    name: 'slug',
    description: 'Slug of example salon: salao-do-guilherme',
  })
  async findBySlug(@Param('slug') slug: string) {
    return this.tenantService.findBySlug(slug);
  }
}
