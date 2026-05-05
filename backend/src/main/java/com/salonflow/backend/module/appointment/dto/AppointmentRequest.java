package com.salonflow.backend.module.appointment.dto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;
public record AppointmentRequest(

        @NotNull(message = "Professional id is required")
        UUID professionalId,

        @NotNull(message = "Service id is required")
        UUID serviceId,

        @NotNull(message = "Customer name is required")
        String customerName,

        @NotNull(message = "Phone is required")
        String customerPhone,

        @NotNull(message = "Date and time is required")
        LocalDateTime scheduledAt,

        String notes

) {
}
