package com.salonflow.backend.controller.dtos;

import java.util.Optional;
import java.util.UUID;

public record CustomerListDTO(UUID id,
                              String name,
                              String phone,
                              Optional<Integer> totalAgendamentos) {
}
