package com.salonflow.backend.controller.dtos;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateDTO(@NotBlank String name,
                                @NotBlank @BRPhoneNumber String phone) { }

