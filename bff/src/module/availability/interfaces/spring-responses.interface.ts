export interface TenantResponse {
  id: string;
  openingTime: string;
  closingTime: string;
}

export interface ServiceDurationResponse {
  id: string;
  durationMinutes: number;
}

export interface AppointmentResponse {
  id: string;
  scheduleAt: string;
  durationMinutes: number;
  status: string;
}

export interface BlockedSlotResponse {
  id: string;
  startAt: string;
  endAt: string;
}
