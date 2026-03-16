package com.salonflow.backend.controller.dtos.professional;

import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProfessionalCreateDTO(

        @NotBlank String name,
        @BRPhoneNumber
        @NotBlank
        String phone,

        @NotNull
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
