package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.ScheduleDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.model.enums.Status;
import com.salonflow.backend.domain.repository.CustomerRepository;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.domain.repository.ScheduleRepository;
import com.salonflow.backend.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ProfessionalRepository professionalRepository;

    private final CustomerRepository customerRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ProfessionalRepository professionalRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;

        this.professionalRepository = professionalRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Schedule createSchedule(ScheduleDTO dto) {

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Professional professional = professionalRepository.findByName(dto.professionalName())
                .orElseThrow(() -> new RuntimeException("Professional not found"));


        Schedule schedule = new Schedule();
        schedule.setCustomer(customer);
        schedule.setProfessional(professional);
        schedule.setScheduleTime(dto.scheduleTime());
        schedule.setStatus(Status.SCHEDULED);

        return scheduleRepository.save(schedule);
    }
}
