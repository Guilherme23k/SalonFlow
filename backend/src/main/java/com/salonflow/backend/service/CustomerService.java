package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerListDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.domain.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer findOrCreateByTelefone(CustomerCreateDTO dto);

    List<CustomerListDTO> listAllCustomers();

    CustomerResponseDTO findCustomerByPhone(String phone);
}
