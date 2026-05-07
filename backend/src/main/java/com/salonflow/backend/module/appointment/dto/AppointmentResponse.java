package com.salonflow.backend.module.appointment.dto;

import com.salonflow.backend.module.appointment.Appointment;
import com.salonflow.backend.module.appointment.AppointmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
public record AppointmentResponse(

        UUID id,
        UUID professionalId,
        String professionalName,
        UUID customerId,
        String customerName,
        String customerPhone,
        UUID serviceId,
        String serviceName,
        LocalDateTime scheduledAt,
        LocalDateTime endsAt,
        Integer durationMinutes,
        BigDecimal price,
        AppointmentStatus status,
        String notes

) {

    public static AppointmentResponse from(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getProfessional().getId(),
                a.getProfessional().getName(),
                a.getCustomer().getId(),
                a.getCustomer().getName(),
                a.getCustomer().getPhone(),
                a.getService().getId(),
                a.getService().getName(),
                a.getScheduledAt(),
                a.getScheduledAt().plusMinutes(a.getDurationMinutes()),
                a.getDurationMinutes(),
                a.getPrice(),
                a.getStatus(),
                a.getNotes()
        );
    }
}
