package com.salonflow.backend.module.serviceduration.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceDurationRequest(

        @NotNull(message = "Professional ID is required")
        UUID professionalId,

        @NotNull(message = "Service ID is required")
        UUID serviceId,

        @NotNull(message = "Duration is required")
        @Positive(message = "Durations must be greater than zero")
        Integer durationMinutes,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price

) {
}
