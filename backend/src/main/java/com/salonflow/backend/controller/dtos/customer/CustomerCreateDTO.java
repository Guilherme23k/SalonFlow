package com.salonflow.backend.controller.dtos.customer;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateDTO(

                                @Schema(example = "Guilherme")
                                @NotBlank String name,

                                @Schema(example = "11912341234")
                                @NotBlank @BRPhoneNumber String phone) { }

