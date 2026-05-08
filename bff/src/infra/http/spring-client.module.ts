import { Module } from '@nestjs/common';
import { HttpModule } from '@nestjs/axios';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { SpringClientService } from './spring-client.service';

@Module({
  imports: [
    HttpModule.registerAsync({
      imports: [ConfigModule],
      inject: [ConfigService],
      useFactory: (config: ConfigService) => ({
        baseURL: config.get<string>('BACKEND_URL'),
        timeout: 5000,
        headers: {
          'X-Internal-Api-Key': config.get<string>('INTERNAL_API_KEY'),
        },
      }),
    }),
  ],
  providers: [SpringClientService],
  exports: [SpringClientService],
})
export class SpringClientModule {}
