/* eslint-disable @typescript-eslint/no-unsafe-return */
/* eslint-disable @typescript-eslint/no-unsafe-assignment */
import { Injectable } from '@nestjs/common';
import { HttpService } from '@nestjs/axios';
import { firstValueFrom } from 'rxjs';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';

@Injectable()
export class SpringClientService {
  constructor(private readonly httpService: HttpService) {}

  async get<T>(
    path: string,
    tenantId: string,
    config?: AxiosRequestConfig,
  ): Promise<T> {
    const response = await firstValueFrom<AxiosResponse<T>>(
      this.httpService.get<T>(path, {
        ...config,
        headers: {
          ...config?.headers,
          'X-Tenant-Id': tenantId,
        },
      }),
    );
    // eslint-disable-next-line @typescript-eslint/no-unsafe-return, @typescript-eslint/no-unsafe-member-access
    return response.data;
  }

  async post<T>(
    path: string,
    tenantId: string,
    body: unknown,
    config?: AxiosRequestConfig,
  ): Promise<T> {
    const response = await firstValueFrom<AxiosResponse<T>>(
      this.httpService.post<T>(path, body, {
        ...config,
        headers: {
          ...config?.headers,
          'X-Tenant-Id': tenantId,
        },
      }),
    );
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
    return response.data;
  }

  async patch<T>(
    path: string,
    tenantId: string,
    body: unknown,
    config?: AxiosRequestConfig,
  ): Promise<T> {
    const response = await firstValueFrom<AxiosResponse<T>>(
      this.httpService.patch<T>(path, body, {
        ...config,
        headers: {
          ...config?.headers,
          'X-Tenant-Id': tenantId,
        },
      }),
    );
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
    return response.data;
  }

  async delete<T>(
    path: string,
    tenantId: string,
    config?: AxiosRequestConfig,
  ): Promise<T> {
    const response = await firstValueFrom<AxiosResponse<T>>(
      this.httpService.delete<T>(path, {
        ...config,
        headers: {
          ...config?.headers,
          'X-Tenant-Id': tenantId,
        },
      }),
    );
    // eslint-disable-next-line @typescript-eslint/no-unsafe-member-access
    return response.data;
  }
}
