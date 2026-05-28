import { Module } from '@nestjs/common';
import { ProfessionalService } from './professional.service';
import { ProfessionalController } from './professional.controller';
import { SpringClientModule } from '../../infra/http/spring-client.module';

@Module({
  imports: [SpringClientModule],
  providers: [ProfessionalService],
  controllers: [ProfessionalController],
})
export class ProfessionalModule {}
