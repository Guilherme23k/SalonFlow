package com.salonflow.backend.module.auth.dto;

public record LoginRequest(
        String email,
        String password
) {
}
