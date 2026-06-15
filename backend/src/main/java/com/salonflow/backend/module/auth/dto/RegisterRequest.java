package com.salonflow.backend.module.auth.dto;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String tenantSlug
) {
}
