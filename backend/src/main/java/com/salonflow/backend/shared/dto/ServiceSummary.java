package com.salonflow.backend.shared.dto;

import com.salonflow.backend.module.service.Service;

import java.util.UUID;

public record ServiceSummary(UUID id,String name,String description) {
    public static ServiceSummary from(Service s){
        return new ServiceSummary(s.getId(), s.getName(), s.getDescription());
    }
}
