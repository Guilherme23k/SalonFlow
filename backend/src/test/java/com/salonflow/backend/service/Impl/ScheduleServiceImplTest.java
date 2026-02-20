package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.ScheduleCreateDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.repository.ScheduleRepository;
import com.salonflow.backend.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private ScheduleCreateDTO scheduleCreateDTO;

    private CustomerCreateDTO createDTO;

    @BeforeEach
    public void setup(){
        createDTO = new CustomerCreateDTO("Guilherme", "11914521452");

    }

    public void shouldCreateSchedule(){

        scheduleCreateDTO = new ScheduleCreateDTO(createDTO, LocalDateTime.now(),"Patricia", "Escova");

        when(scheduleRepository.save(any())).thenReturn(createDTO);

        Schedule scheduleResults = scheduleService.createSchedule(scheduleCreateDTO);

    }

}