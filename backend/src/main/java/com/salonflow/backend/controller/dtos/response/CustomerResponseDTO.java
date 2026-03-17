package com.salonflow.backend.controller.dtos.response;

import com.salonflow.backend.controller.dtos.customer.CustomerCreateDTO;
import com.salonflow.backend.domain.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponseDTO(
                                  String name,
                                  String phone
                                  ) {

    public static CustomerResponseDTO toDTO(Customer customer){
        return new CustomerResponseDTO(
                customer.getName(),
                customer.getPhone()
        );
    }
}

