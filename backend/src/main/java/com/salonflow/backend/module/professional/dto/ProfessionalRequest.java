package com.salonflow.backend.module.professional.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfessionalRequest(

        @NotBlank(message = "Name is required")
        String name,
        String phone,
        Double commissionPercentage


) {
}
