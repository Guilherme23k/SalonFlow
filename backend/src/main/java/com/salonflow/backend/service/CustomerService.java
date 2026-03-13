package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.customer.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.customer.CustomerDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.domain.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    Customer findOrCreateByPhone(CustomerCreateDTO dto);

    List<CustomerDTO> listAllCustomers();

    CustomerResponseDTO findCustomerByPhone(String phone);

    CustomerDTO findWithSchedules(UUID id);
}
