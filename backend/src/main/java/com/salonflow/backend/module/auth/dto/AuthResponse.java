package com.salonflow.backend.module.auth.dto;

import java.util.UUID;

public record AuthResponse(
        String token,
        UUID tenantId,
        String ownerName
) {
}
