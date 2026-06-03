export interface Tenant {
  id: string;
  name: string;
  slug: string;
  openingTime: string;
  closingTime: string;
  active: boolean;
}

export interface Professional {
  id: string;
  name: string;
  phone: string | null;
  commissionPercentage: number | null;
  active: boolean;
}

export interface Service {
  id: string;
  name: string;
  description: string | null;
  active: boolean;
}

export interface ServiceDuration {
  id: string;
  professionalId: string;
  professionalName: string;
  serviceId: string;
  serviceName: string;
  serviceDescription: string;
  durationMinutes: string;
  price: number;
  active: boolean;
}

export interface AvailableSlot {
  time: string;
  available: boolean;
}

export interface CreateAppointmentPayload {
  professionalId: string;
  serviceId: string;
  customerName: string;
  customerPhone: string;
  scheduledAt: string;
  notes?: string;
}

export interface AppointmentResponse {
  id: string;
  professionalId: string;
  professionalName: string;
  customerId: string;
  customerName: string;
  customerPhone: string;
  serviceId: string;
  serviceName: string;
  scheduledAt: string;
  endsAt: string;
  durationMinutes: number;
  price: number;
  status: 'CONFIRMED' | 'CANCELED';
  notes: string | null;
}