package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.ScheduleDTO;
import com.salonflow.backend.domain.model.Schedule;

public interface ScheduleService {
    Schedule createSchedule(ScheduleDTO dto);
}
