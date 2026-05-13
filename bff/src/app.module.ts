import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { SpringClientModule } from './infra/http/spring-client.module';
import { AvailabilityModule } from './module/availability/availability.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    SpringClientModule,
    AvailabilityModule,
  ],
})
export class AppModule {}
