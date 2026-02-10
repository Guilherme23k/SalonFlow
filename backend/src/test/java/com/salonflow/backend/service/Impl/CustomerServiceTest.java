package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerListDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.repository.CustomerRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

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

    @Test
    public void shouldTransferToDTOAndListCustomerListDTO(){

        Customer id1Customer = new Customer();
        id1Customer.setId(UUID.randomUUID());
        id1Customer.setName("Guigas");
        id1Customer.setPhone("11902939485");

        Customer id2Customer = new Customer();
        id2Customer.setId(UUID.randomUUID());
        id2Customer.setName("Guigas");
        id2Customer.setPhone("11902939485");


        when(customerRepository.findAll()).thenReturn(List.of(id1Customer, id2Customer));

        CustomerListDTO customerListDTO1 = new CustomerListDTO(id1Customer.getId(), id1Customer.getName(), id1Customer.getPhone(), Optional.empty());
        CustomerListDTO customerListDTO2 = new CustomerListDTO(id2Customer.getId(), id2Customer.getName(), id2Customer.getPhone(), Optional.empty());

        List<CustomerListDTO> expectedList = List.of(customerListDTO1, customerListDTO2);

        List<CustomerListDTO> realList = customerService.listAllCustomers();

        assertThat(realList)
                .usingRecursiveComparison()
                .isEqualTo(expectedList);

    }



}