import axios from "axios";

import type {
  Tenant,
  AvailableSlot,
  CreateAppointmentPayload,
  AppointmentResponse,
  Professional,
  Service,
} from '../types';

const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL as string,
})

api.interceptors.request.use((config) =>{
    const tenantId = sessionStorage.getItem('tenantId');
    if (tenantId) {
        config.headers['X-Tenant-Id'] = tenantId;
    }
    return config;
})

export async function resolveTenant(slug:string): Promise<Tenant> {
    const { data } = await api.get<Tenant>(`/api/v1/tenants/slug/${slug}`);
    sessionStorage.setItem('tenantId', data.id);
    return data;
}

export async function getProfessional(): Promise<Professional[]> {
    const { data } = await api.get<Professional[]>('api/v1/professionals');
    return data;
}

export async function getService(): Promise<Service[]> {
    const { data } = await api.get<Service[]>('/api/v1/services');
    return data;
    
}

export async function getAvailability(professionalId: string,
    serviceId:string,
    date: string,
): Promise<AvailableSlot[]> {
    const { data } = await api.get<AvailableSlot[]>('/api/v1/availability', {
        params: { professionalId, serviceId, date },
    });
    return data;
}

export async function createAppointment(payload:CreateAppointmentPayload,
): Promise<AppointmentResponse> {
    const { data } = await api.post<AppointmentResponse>('/api/v1/appointments', payload);
    return data;
}