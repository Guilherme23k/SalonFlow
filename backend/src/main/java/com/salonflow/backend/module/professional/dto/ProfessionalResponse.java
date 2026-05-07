package com.salonflow.backend.module.professional.dto;

import com.salonflow.backend.module.professional.Professional;

import java.util.UUID;

public record ProfessionalResponse(

        UUID id,
        String name,
        String phone,
        Double commissionPercentage,
        Boolean active
) {

    public static ProfessionalResponse from (Professional professional){
        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getPhone(),
                professional.getCommissionPercentage(),
                professional.getActive()
        );
    }

}
