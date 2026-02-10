package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CustomerCreateDTO dto;

    @BeforeEach
    public void setup(){

        dto = new CustomerCreateDTO("Guilherme", "11981131645");
    }

    @Test
    public void shouldCreateCustomerWhenNotExistsInDB(){

        Customer customerEntidade = new Customer();
        customerEntidade.setId(UUID.randomUUID());
        customerEntidade.setName(dto.name());
        customerEntidade.setPhone(dto.phone());


        when(customerRepository.findByPhone(dto.phone())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customerEntidade);



        Customer results = customerService.findOrCreateByTelefone(dto);

        assertNotNull(results.getId());
        assertEquals(dto.name(), results.getName());
        verify(customerRepository).save(any(Customer.class));

    }

    @Test
    public void shouldReturnCustomerWhenExistsInDB(){

        Customer customerEntidade = new Customer();
        customerEntidade.setId(UUID.randomUUID());
        customerEntidade.setName(dto.name());
        customerEntidade.setPhone(dto.phone());

        when(customerRepository.findByPhone(dto.phone())).thenReturn(Optional.of(customerEntidade));

        Customer results = customerService.findOrCreateByTelefone(dto);

        assertNotNull(results.getId());
        assertEquals(dto.phone(), results.getPhone());
        verify(customerRepository, never()).save(any());

    }



}