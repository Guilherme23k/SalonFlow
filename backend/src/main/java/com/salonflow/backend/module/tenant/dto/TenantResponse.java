package com.salonflow.backend.module.tenant.dto;

import com.salonflow.backend.module.tenant.Tenant;

import java.time.LocalTime;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String name,
        String slug,
        LocalTime openingTime,
        LocalTime closingTime,
        Boolean active
) {

    public static TenantResponse from(Tenant tenant){
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getSlug(),
                tenant.getOpeningTime(),
                tenant.getClosingTime(),
                tenant.getActive()
        );
    }

}
