package com.salonflow.backend.controller.dtos.professional;

import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.validations.interfaces.BRPhoneNumber;

public record ProfessionalCreateDTO(

        String name,
        @BRPhoneNumber
        String phone,
        Double commisionPercentage

) {
        public static ProfessionalCreateDTO toDTO (Professional professional){
                return new ProfessionalCreateDTO(
                        professional.getName(),
                        professional.getPhone(),
                        professional.getCommissionPercentage()
                );
        }
}
