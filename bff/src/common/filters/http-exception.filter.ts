import {
  ExceptionFilter,
  Catch,
  ArgumentsHost,
  HttpException,
  HttpStatus,
  Logger,
} from '@nestjs/common';
import { Response } from 'express';

@Catch()
export class HttpExceptionFilter implements ExceptionFilter {
  private readonly logger = new Logger(HttpExceptionFilter.name);

  catch(exception: unknown, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();

    const status =
      exception instanceof HttpException
        ? exception.getStatus()
        : HttpStatus.INTERNAL_SERVER_ERROR;

    const message =
      exception instanceof HttpException
        ? exception.message
        : 'Erro interno do servidor';

    // eslint-disable-next-line @typescript-eslint/no-unsafe-enum-comparison
    if (status === HttpStatus.INTERNAL_SERVER_ERROR) {
      this.logger.error(
        'Erro inesperado',
        exception instanceof Error ? exception.stack : exception,
      );
    }

    response.status(status).json({
      status,
      message,
      timestamp: new Date().toISOString(),
    });
  }
}
