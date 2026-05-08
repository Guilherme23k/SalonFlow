import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { SpringClientModule } from './infra/http/spring-client.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    SpringClientModule,
  ],
})
export class AppModule {}
