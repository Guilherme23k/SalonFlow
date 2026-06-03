package com.salonflow.backend.shared.dto;

import com.salonflow.backend.module.customer.Customer;

import java.util.UUID;

public record CustomerSummary(UUID id,String name,String phone) {
    public static CustomerSummary from(Customer c){
        return new CustomerSummary(c.getId(), c.getName(), c.getPhone());
    }
}
