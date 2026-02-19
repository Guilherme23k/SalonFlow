package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerListDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.repository.CustomerRepository;
import com.salonflow.backend.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
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

    @Override
    public List<CustomerListDTO> listAllCustomers(){

        return customerRepository.findAll().stream()
                .map(customer -> new CustomerListDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        Optional.empty())).collect(Collectors.toList());

    }


    @Override
    public CustomerResponseDTO findCustomerByPhone(String phone) {


        return customerRepository.findByPhone(phone)
                .map(customer -> new CustomerResponseDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getCreated_at()
                )).orElseThrow(() -> new RuntimeException("Customer not found"));

    }
}
