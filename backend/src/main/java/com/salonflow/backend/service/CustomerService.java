package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    CustomerDTO findOrCreateByTelefone(CustomerCreateDTO dto);

    List<CustomerDTO> listAllCustomers();

    CustomerResponseDTO findCustomerByPhone(String phone);

    CustomerDTO findWithSchedules(UUID id);
}
