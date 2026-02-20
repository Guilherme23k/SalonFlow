package com.salonflow.backend.controller.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleCreateDTO(CustomerCreateDTO createDTO,
                                LocalDateTime scheduleTime,
                                UUID customerId,
                                String professionalName,
                                String professionalService) {
}
