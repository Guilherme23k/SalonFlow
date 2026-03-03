package com.salonflow.backend.controller.dtos;

import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerDTO(UUID id,
                          String name,
                          String phone,
                          LocalDateTime createdAt,
                          List<Schedule> schedules) {

    public static CustomerDTO toDTO(Customer customer){
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getCreated_at(),
                customer.getSchedules()
        );
    }
}
