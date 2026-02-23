package com.salonflow.backend.controller.dtos;

import com.salonflow.backend.domain.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerDTO(UUID id,
                          String name,
                          String phone,
                          LocalDateTime createdAt,
                          List<ScheduleDTO> schedules) {
}
