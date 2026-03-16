package com.salonflow.backend.controller.dtos.response;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Professional;

public record ProfessionalResponseDTO(

        String name,
        String phone

) {

    public static ProfessionalResponseDTO toResponseDTO(Professional professional){
        return new  ProfessionalResponseDTO(
                professional.getName(),
                professional.getPhone()
        );
    }

    public static ProfessionalResponseDTO fromCreateToResponse(ProfessionalCreateDTO professionalCreateDTO){
        return new ProfessionalResponseDTO(
                professionalCreateDTO.name(),
                professionalCreateDTO.phone()
        );
    }
}
