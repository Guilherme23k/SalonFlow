package com.salonflow.backend.module.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public record TenantRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Slug is required")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug should contain only lowercase letters, numbers, and hyphens.")
        String slug,
        @NotNull(message = "Opening time is required")
        LocalTime openingTime,
        @NotNull(message = "Closing time is required")
        LocalTime closingTime
) {
}
