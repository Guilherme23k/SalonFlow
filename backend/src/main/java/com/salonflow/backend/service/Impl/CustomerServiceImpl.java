package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerDTO;
import com.salonflow.backend.controller.dtos.ScheduleDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.repository.CustomerRepository;
import com.salonflow.backend.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<CustomerDTO> listAllCustomers() {

        return customerRepository.findAll().stream()
                .map(customer -> new CustomerDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getPhone(),
                        customer.getCreated_at(),
                        customer.getSchedules()
                                .stream()
                                .map(ScheduleDTO::fromEntity)
                                .toList()
                ))
                .toList();
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

    @Override
    public CustomerDTO findWithSchedules(UUID id) {

        Optional<Customer> customerOptional = customerRepository.findByIDWithSchedules(id);

        if (customerOptional.isEmpty()){
            throw new RuntimeException("Customer not found");
        }

        Customer customer = customerOptional.get();

        List<ScheduleDTO> scheduleDTOs = customer.getSchedules()
                .stream()
                .map(ScheduleDTO::fromEntity)
                .toList();

        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getCreated_at(),
                scheduleDTOs
        );


    }
}
