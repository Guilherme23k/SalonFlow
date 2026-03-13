package com.salonflow.backend.controller.dtos.schedule;

import com.salonflow.backend.domain.model.Schedule;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduleDTO(
                                UUID customerId,
                                LocalDateTime scheduleTime,
                                String professionalName,
                                String serviceName,
                                Double price) {

    public static ScheduleDTO fromEntity(Schedule schedule){
        return new ScheduleDTO(
                schedule.getId(),
                schedule.getScheduleTime(),
                schedule.getProfessional().getName(),
                schedule.getProfessionalServices().getName(),
                schedule.getProfessionalServices().getPrice()
        );
    }
}
