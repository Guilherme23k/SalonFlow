package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerListDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findOrCreateByTelefone(CustomerCreateDTO dto){


        Optional<Customer> customerGetByPhone = customerRepository.findByPhone(dto.phone());

        if (customerGetByPhone.isPresent()){
            return customerGetByPhone.get();
        }


        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setPhone(dto.phone());

        return customerRepository.save(customer);
    }

    public List<CustomerListDTO> listAllCustomers(){

        return customerRepository.findAll().stream()
                .map(customer -> new CustomerListDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        Optional.empty())).collect(Collectors.toList());

    }


}
