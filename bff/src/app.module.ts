import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { SpringClientModule } from './infra/http/spring-client.module';
import { AvailabilityModule } from './module/availability/availability.module';
import { AppointmentModule } from './module/appointment/appointment.module';
import { TenantModuleTsModule } from './module/tenant/tenant.module';
import { TenantServiceTsService } from './module/tenant/tenant.service';
import { TenantControllerTsController } from './module/tenant/tenant.controller';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    SpringClientModule,
    AvailabilityModule,
    AppointmentModule,
    TenantModuleTsModule,
  ],
  providers: [TenantServiceTsService],
  controllers: [TenantControllerTsController],
})
export class AppModule {}
