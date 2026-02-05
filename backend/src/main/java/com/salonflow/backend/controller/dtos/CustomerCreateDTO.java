package com.salonflow.backend.controller.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerCreateDTO(@NotBlank String name,
                                @NotBlank @Pattern(regexp = "^\\s*(\\d{2}|\\d{0})[-. ]?(\\d{5}|\\d{4})[-. ]?(\\d{4})[-. ]?\\s*$\n") String phone) { }
