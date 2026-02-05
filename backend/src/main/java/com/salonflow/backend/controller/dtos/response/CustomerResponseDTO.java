package com.salonflow.backend.controller.dtos.response;

import com.salonflow.backend.domain.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponseDTO(UUID id,
                                  String name,
                                  String phone,
                                  LocalDateTime created_at
                                  ) {

    public static CustomerResponseDTO toDTO(Customer customer){
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getCreated_at()
        );
    }
}

