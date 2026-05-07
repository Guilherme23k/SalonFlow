package com.salonflow.backend.module.blockedslots.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedSlotRequest(

        @NotNull(message = "Professional ID is required")
        UUID professionalId,

        @NotNull(message = "Block start time is required")
        LocalDateTime startAt,

        @NotNull(message = "Block end time is required")
        LocalDateTime endAt,

        String reason

) {
}
