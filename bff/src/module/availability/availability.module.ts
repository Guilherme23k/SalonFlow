import { Module } from '@nestjs/common';
import { SpringClientModule } from '../../infra/http/spring-client.module';
import { AvailabilityController } from './availability.controller';
import { AvailabilityService } from './availability.service';

@Module({
  imports: [SpringClientModule],
  controllers: [AvailabilityController],
  providers: [AvailabilityService],
})
export class AvailabilityModule {}
