package com.salonflow.backend.controller.dtos;

import java.time.LocalDateTime;

public record ScheduleCreateDTO(CustomerCreateDTO createDTO,
                                LocalDateTime scheduleTime,
                                String professionalName,
                                String professionalService) {
}
