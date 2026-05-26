import { IsNotEmpty, IsOptional, IsString, Matches } from 'class-validator';

export class CreateAppointmentDto {
  @IsNotEmpty({ message: 'professionalId is required' })
  @IsString()
  professionalId!: string;

  @IsNotEmpty({ message: 'serviceId is required' })
  @IsString()
  serviceId!: string;

  @IsNotEmpty({ message: 'Customer name is required' })
  @IsString()
  customerName!: string;

  @IsNotEmpty({ message: 'Customer phone is required' })
  @IsString()
  customerPhone!: string;

  @IsNotEmpty({ message: 'Date and time is required' })
  @Matches(/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$/, {
    message: 'scheduledAt is must be on ISO format: YYYY-MM-DDTHH:mm:ss',
  })
  scheduledAt!: string;

  @IsOptional()
  @IsString()
  notes?: string;
}
