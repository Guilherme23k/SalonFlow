package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.ScheduleCreateDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.model.enums.Status;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.domain.repository.ScheduleRepository;
import com.salonflow.backend.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfessionalRepository professionalRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ProfessionalRepository professionalRepository) {
        this.scheduleRepository = scheduleRepository;

        this.professionalRepository = professionalRepository;
    }

    public Schedule createSchedule(ScheduleCreateDTO dto) {

        Schedule schedule = new Schedule();
        schedule.setScheduleTime(dto.scheduleTime());
        Professional prof = professionalRepository.findByName(dto.professionalName())
                .orElseThrow(() -> new RuntimeException("Professional not found"));
        schedule.setProfessional(prof);
        schedule.setStatus(Status.SCHEDULED);

        return scheduleRepository.save(schedule);

    }
}
