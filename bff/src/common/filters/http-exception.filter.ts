/* eslint-disable @typescript-eslint/no-unsafe-enum-comparison */
/* eslint-disable @typescript-eslint/no-unsafe-member-access */
// src/common/filters/http-exception.filter.ts
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

    // Extrai a mensagem real — incluindo erros de validação
    let message: string | object = 'Erro interno do servidor';
    if (exception instanceof HttpException) {
      const exceptionResponse = exception.getResponse();
      // eslint-disable-next-line @typescript-eslint/no-unsafe-assignment
      message =
        typeof exceptionResponse === 'string'
          ? exceptionResponse
          : ((exceptionResponse as any).message ?? exception.message);
    }

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
