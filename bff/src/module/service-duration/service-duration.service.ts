import { Injectable } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';

@Injectable()
export class ServiceDurationService {
  constructor(private readonly springClient: SpringClientService) {}

  async findAllServicesByProfessional(
    tenantId: string,
    professionalId: string,
  ) {
    return this.springClient.get(
      `/api/v1/service-durations/${professionalId}/services`,
      tenantId,
    );
  }
}
