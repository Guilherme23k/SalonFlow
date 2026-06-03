import { Module } from '@nestjs/common';
import { ServiceDurationController } from './service-duration.controller';
import { ServiceDurationService } from './service-duration.service';
import { SpringClientModule } from '../../infra/http/spring-client.module';

@Module({
  imports: [SpringClientModule],
  controllers: [ServiceDurationController],
  providers: [ServiceDurationService],
})
export class ServiceDurationModule {}
