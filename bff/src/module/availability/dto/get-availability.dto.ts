import { IsNotEmpty, IsString, Matches } from 'class-validator';

export class GetAvailabilityDTO {
  @IsNotEmpty({ message: 'professionalId is required' })
  @IsString()
  professionalId!: string;

  @IsNotEmpty({ message: 'serviceId is required' })
  @IsString()
  serviceId!: string;

  @IsNotEmpty({ message: 'date is required' })
  @Matches(/^\d{2}-\d{2}-\d{4}$/, { message: 'date must be on DD-MM-YYYY' })
  date!: string;
}
