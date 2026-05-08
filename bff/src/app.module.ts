import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { ConfigModule } from '@nestjs/config';
import { SpringClientModule } from './infra/http/spring-client.module';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),
    SpringClientModule,
  ],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
