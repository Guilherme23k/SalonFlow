import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { SpringClientModule } from './infra/http/spring-client.module';
import { AvailabilityModule } from './module/availability/availability.module';
import { AppointmentModule } from './module/appointment/appointment.module';
import { TenantModule } from './module/tenant/tenant.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    SpringClientModule,
    AvailabilityModule,
    AppointmentModule,
    TenantModule,
  ],
})
export class AppModule {}
