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

@Controller('api/v1/availability')
@UseInterceptors(TenantInterceptor)
export class AvailabilityController {
  constructor(private readonly service: AvailabilityService) {}

  @Get()
  async getAvailability(
    @Headers('x-tenant-id') tenantId: string,
    @Query() dto: GetAvailabilityDTO,
  ) {
    return this.service.getAvailableSlots(tenantId, dto);
  }
}
