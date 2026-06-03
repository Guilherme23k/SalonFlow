package com.salonflow.backend.module.appointment.dto;

import com.salonflow.backend.module.appointment.Appointment;
import com.salonflow.backend.module.appointment.AppointmentStatus;
import com.salonflow.backend.module.customer.Customer;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
public record AppointmentResponse(

        UUID id,
        Professional professional,
        Customer customer,
        Service service,
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
                a.getProfessional(),
                a.getCustomer(),
                a.getService(),
                a.getScheduledAt(),
                a.getScheduledAt().plusMinutes(a.getDurationMinutes()),
                a.getDurationMinutes(),
                a.getPrice(),
                a.getStatus(),
                a.getNotes()
        );
    }
}
