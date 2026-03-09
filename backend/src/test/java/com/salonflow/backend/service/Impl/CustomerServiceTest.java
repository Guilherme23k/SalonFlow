package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerDTO;
import com.salonflow.backend.controller.dtos.ScheduleDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.domain.model.Customer;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.model.ProfessionalServices;
import com.salonflow.backend.domain.model.Schedule;
import com.salonflow.backend.domain.repository.CustomerRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Professional professional;

    private ProfessionalServices corte;

    private CustomerCreateDTO dto;

    private CustomerResponseDTO dto2;

    @BeforeEach
    public void setup(){

        dto = new CustomerCreateDTO("Guilherme", "11981131645");

        professional = new Professional();
        professional.setId(UUID.randomUUID());
        professional.setName("Patricia");
        professional.setServices(new ArrayList<>());

        corte =  new ProfessionalServices();
        corte.setId(1L);
        corte.setName("Corte degrade");
        corte.setProfessional(professional);
        corte.setPrice(50.00);

        professional.getServices().add(corte);
    }

    @Test
    public void shouldCreateCustomerWhenNotExistsInDB(){

        Customer customerEntidade = new Customer();
        customerEntidade.setId(UUID.randomUUID());
        customerEntidade.setName(dto.name());
        customerEntidade.setPhone(dto.phone());


        when(customerRepository.findByPhone(dto.phone())).thenReturn(Optional.empty());
        when(customerRepository.save(any(Customer.class))).thenReturn(customerEntidade);



        CustomerDTO results = customerService.findOrCreateByTelefone(dto);

        assertNotNull(results.id());
        assertEquals(dto.name(), results.name());
        verify(customerRepository).save(any(Customer.class));

    }

    @Test
    public void shouldReturnCustomerWhenExistsInDB(){

        Customer customerEntidade = new Customer();
        customerEntidade.setId(UUID.randomUUID());
        customerEntidade.setName(dto.name());
        customerEntidade.setPhone(dto.phone());

        when(customerRepository.findByPhone(dto.phone())).thenReturn(Optional.of(customerEntidade));

        CustomerDTO results = customerService.findOrCreateByTelefone(dto);

        assertNotNull(results.id());
        assertEquals(dto.phone(), results.phone());
        verify(customerRepository, never()).save(any());

    }

    @Test
    public void shouldTransferToDTOAndListCustomerListDTO(){

        Customer id1Customer = new Customer();
        id1Customer.setId(UUID.randomUUID());
        id1Customer.setName("Guigas");
        id1Customer.setPhone("11902939485");
        id1Customer.setSchedules(new ArrayList<>());

        Customer id2Customer = new Customer();
        id2Customer.setId(UUID.randomUUID());
        id2Customer.setName("Guigas");
        id2Customer.setPhone("11902939485");
        id2Customer.setSchedules(new ArrayList<>());

        List<ScheduleDTO> scheduleDTOS1 = id1Customer.getSchedules()
                        .stream()
                                .map(ScheduleDTO::fromEntity)
                                        .toList();

        List<ScheduleDTO> scheduleDTOS2 = id2Customer.getSchedules()
                        .stream()
                                .map(ScheduleDTO::fromEntity)
                                        .toList();


        when(customerRepository.findAll()).thenReturn(List.of(id1Customer, id2Customer));

        CustomerDTO customerDTO1 = new CustomerDTO(id1Customer.getId(), id1Customer.getName(), id1Customer.getPhone(), id1Customer.getCreated_at(), scheduleDTOS1);
        CustomerDTO customerDTO2 = new CustomerDTO(id2Customer.getId(), id2Customer.getName(), id2Customer.getPhone(), id2Customer.getCreated_at(), scheduleDTOS2);

        List<CustomerDTO> expectedList = List.of(customerDTO1, customerDTO2);

        List<CustomerDTO> realList = customerService.listAllCustomers();

        assertThat(realList)
                .usingRecursiveComparison()
                .isEqualTo(expectedList);

    }

    @Test
    public void shouldFindCustomerByPhone(){

        String phone = "11938383838";

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Guilherme");
        customer.setPhone(phone);



        when(customerRepository.findByPhone(customer.getPhone())).thenReturn(Optional.of(customer));

        dto2 = new CustomerResponseDTO(customer.getId(), customer.getName(), customer.getPhone(), customer.getCreated_at());

        CustomerResponseDTO customerResults = customerService.findCustomerByPhone(phone);

        assertThat(customerResults)
                .usingRecursiveComparison()
                .isEqualTo(dto2);

    }


    @Test
    public void shouldFindAllSchedulesByCustomerId(){

        UUID customerID = UUID.randomUUID();

        Customer customer = new Customer();
        customer.setId(customerID);
        customer.setName("Guilherme");
        customer.setSchedules(new ArrayList<>());

        Schedule schedule1 = new Schedule();
        schedule1.setCustomer(customer);
        schedule1.setScheduleTime(LocalDateTime.now());
        schedule1.setProfessional(professional);
        schedule1.setProfessionalServices(corte);



        Schedule schedule2 = new Schedule();
        schedule2.setCustomer(customer);
        schedule2.setScheduleTime(LocalDateTime.now().plusDays(1));
        schedule2.setProfessional(professional);
        schedule2.setProfessionalServices(corte);

        List<Schedule> schedules = List.of(schedule1, schedule2);

        customer.setSchedules(schedules);


        when(customerRepository.findByIDWithSchedules(customer.getId())).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.findWithSchedules(customerID);

        assertNotNull(result);
        assertEquals("Guilherme", result.name());
        assertFalse(result.schedules().isEmpty());
        assertEquals(2, result.schedules().size());

    }


}