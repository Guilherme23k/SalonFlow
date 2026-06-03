package com.salonflow.backend.shared.dto;

import com.salonflow.backend.module.professional.Professional;

import java.util.UUID;

public record ProfessionalSummary(UUID id, String name) {

    public static ProfessionalSummary from (Professional p){
        return new ProfessionalSummary(p.getId(), p.getName());
    }

}
