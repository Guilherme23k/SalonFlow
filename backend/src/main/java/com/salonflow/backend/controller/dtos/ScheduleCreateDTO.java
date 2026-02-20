package com.salonflow.backend.controller.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleCreateDTO(
                                UUID customerId,
                                LocalDateTime scheduleTime,
                                String professionalName,
                                Long professionalService) {
}
