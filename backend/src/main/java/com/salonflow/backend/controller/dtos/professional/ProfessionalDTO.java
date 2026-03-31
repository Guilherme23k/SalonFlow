package com.salonflow.backend.controller.dtos.professional;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import jakarta.validation.constraints.NotBlank;

public record ProfessionalDTO(@NotBlank String name, @BRPhoneNumber String phone) {
}
