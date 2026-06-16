import { Injectable, UnauthorizedException } from '@nestjs/common';
import { SpringClientService } from '../../infra/http/spring-client.service';
import { RegisterRequest } from './dto/register.dto';
import { LoginRequest } from './dto/login.dto';

@Injectable()
export class AuthService {
  constructor(private readonly spring: SpringClientService) {}

  async register(registerData: RegisterRequest): Promise<void> {
    await this.spring.postAuth('/api/v1/auth', registerData);
  }

  async login(loginData: LoginRequest): Promise<string> {
    const data = await this.spring.postAuth('/api/v1/auth', loginData);

    if (!data) {
      throw new UnauthorizedException('Failed to get auth token');
    }

    return data;
  }
}
