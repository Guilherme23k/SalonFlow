import { Injectable } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';

@Injectable()
export class ServiceService {
  constructor(private readonly springClient: SpringClientService) {}

  async findAll(tenantId: string) {
    return this.springClient.get('/api/v1/services', tenantId);
  }
}
