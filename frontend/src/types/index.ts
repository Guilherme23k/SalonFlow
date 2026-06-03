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
  professional: {
    id: string;
    name: string;
  }
  customer: {
    id: string;
    name: string;
    phone: string;
  }
  service: {
    id: string;
    name: string;
  }
  scheduledAt: string;
  endsAt: string;
  durationMinutes: number;
  price: number;
  status: 'CONFIRMED' | 'CANCELED';
  notes: string | null;
}