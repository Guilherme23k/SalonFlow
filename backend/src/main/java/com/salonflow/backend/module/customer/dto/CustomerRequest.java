package com.salonflow.backend.module.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Phone is required")
        String phone

) {
}
