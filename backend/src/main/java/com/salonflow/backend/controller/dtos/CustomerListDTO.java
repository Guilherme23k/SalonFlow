package com.salonflow.backend.controller.dtos;

import java.util.List;
import java.util.UUID;

public record CustomerListDTO(UUID id,
                              String name,
                              String phone,
                              List<int> totalAgendamentos) {
}
