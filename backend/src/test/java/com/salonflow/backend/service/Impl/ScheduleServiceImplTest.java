package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.ScheduleCreateDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.model.ProfessionalServices;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.repository.CustomerRepository;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.domain.repository.ScheduleRepository;
import com.salonflow.backend.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ProfessionalRepository professionalRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private ScheduleCreateDTO scheduleCreateDTO;

    private CustomerCreateDTO customerDTO;

    private Professional professional;

    private ProfessionalServices corte;




    @BeforeEach
    public void setup(){

        professional = new Professional();
        professional.setId(UUID.randomUUID());
        professional.setName("Patricia");
        professional.setServices(new ArrayList<>());

        corte =  new ProfessionalServices();
        corte.setId(1L);
        corte.setName("Corte degrade");
        corte.setProfessional(professional);
        corte.setPrice(50.00);

        professional.getServices().add(corte);

        customerDTO = new CustomerCreateDTO("Guilherme", "11914521452");


    }

    @Test
    public void shouldCreateSchedule(){

        Customer customerFake = new Customer();
        customerFake.setId(UUID.randomUUID());
        customerFake.setName(customerDTO.name());
        customerFake.setPhone(customerDTO.phone());

        scheduleCreateDTO = new ScheduleCreateDTO(
                customerFake.getId(),
                LocalDateTime.now().plusDays(1),
                professional.getName(),
                corte.getId()

        );


        when(customerRepository.findById(scheduleCreateDTO.customerId())).
                thenReturn(java.util.Optional.of(customerFake));

        when(professionalRepository.findByName(scheduleCreateDTO.professionalName()))
                .thenReturn(java.util.Optional.of(professional));

        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);

        Schedule scheduleResults = scheduleService.createSchedule(scheduleCreateDTO);

        assertNotNull(scheduleResults);
        assertEquals(customerFake.getName(), scheduleResults.getCustomer().getName());
        assertEquals(professional.getName(), scheduleResults.getProfessional().getName());
        verify(scheduleRepository, times(1)).save(any(Schedule.class));

    }

}