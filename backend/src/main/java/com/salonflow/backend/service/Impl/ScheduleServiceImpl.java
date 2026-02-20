package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.ScheduleCreateDTO;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.repository.ScheduleRepository;
import com.salonflow.backend.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(ScheduleCreateDTO scheduleCreateDTO) {

        Schedule schedule = new Schedule();

        return scheduleRepository.save(schedule);

    }
}
