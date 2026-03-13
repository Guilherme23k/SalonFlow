package com.salonflow.backend.controller.dtos.professional;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;

public record ProfessionalCreateDTO(

        String name,
        @BRPhoneNumber
        String phone,
        Double commisionPercentage

) {
}
