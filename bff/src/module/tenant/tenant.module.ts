import { Module } from '@nestjs/common';
import { SpringClientModule } from '../../infra/http/spring-client.module';
import { TenantController } from './tenant.controller';
import { TenantService } from './tenant.service';

@Module({
  imports: [SpringClientModule],
  controllers: [TenantController],
  providers: [TenantService],
})
export class TenantModuleTsModule {}
