package com.salonflow.backend.module.service.dto;

import jakarta.validation.constraints.NotBlank;

public record ServiceRequest(

        @NotBlank(message = "Name is required")
        String name,
        String description
) {
}
