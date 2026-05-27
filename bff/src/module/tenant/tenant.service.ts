import { Injectable } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';

export interface TenantPublicResponse {
  id: string;
  name: string;
  slug: string;
  openingTime: string;
  closingTime: string;
}

@Injectable()
export class TenantService {
  constructor(private readonly springClient: SpringClientService) {}

  async findBySlug(slug: string): Promise<TenantPublicResponse> {
    return this.springClient.get<TenantPublicResponse>(
      `/api/v1/tenants/slug/${slug}`,
      'public',
    );
  }
}
