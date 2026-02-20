package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.ScheduleCreateDTO;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.service.Impl.ScheduleServiceImpl;

public interface ScheduleService {
    Schedule createSchedule(ScheduleCreateDTO dto);
}
