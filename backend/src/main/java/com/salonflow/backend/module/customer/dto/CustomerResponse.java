package com.salonflow.backend.module.customer.dto;

import com.salonflow.backend.module.customer.Customer;

import java.util.UUID;

public record CustomerResponse(

        UUID id,
        String name,
        String phone

) {

    public static CustomerResponse from(Customer customer){

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getPhone()
        );
    }
}
