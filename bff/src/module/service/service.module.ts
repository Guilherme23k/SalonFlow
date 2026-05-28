import { Module } from '@nestjs/common';
import { ServiceService } from './service.service';
import { ServiceController } from './service.controller';
import { SpringClientModule } from '../../infra/http/spring-client.module';

@Module({
  imports: [SpringClientModule],
  providers: [ServiceService],
  controllers: [ServiceController],
})
export class ServiceModule {}
