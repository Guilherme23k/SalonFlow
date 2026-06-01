package com.salonflow.backend.module.serviceduration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;
import com.salonflow.backend.module.serviceduration.dto.ServiceDurationResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ServiceDurationServiceTest {

    @Mock
    private ServiceDurationRepository repository;

    @InjectMocks
    private ServiceDurationService service;

    private Professional professional;

    private Service service1;

    @BeforeEach
    void setup(){

        TenantContext.setCurrentTenant(UUID.randomUUID());

        professional = new Professional("Guilherme",
                "11928374659",
                10.00,
                true);

        service1 = new Service("Escova",
                "Escova e prancha",
                true);

    }

    @AfterEach
    void tearDown(){
        TenantContext.clear();
    }

    @Test
    @DisplayName("Must return all services by a professionalId")
    void findAllServicesByProfessionalId() {

    UUID fakeProfessionalId = UUID.randomUUID();

    ServiceDuration serviceDuration = new ServiceDuration(professional, service1, 40, BigDecimal.valueOf(30));
    List<ServiceDuration> serviceDurationList = List.of(serviceDuration);


    when(repository.findAllServicesByProfessionalId(fakeProfessionalId)).thenReturn(serviceDurationList);

    List<ServiceDurationResponse> result = service.findAllServicesByProfessionalId(fakeProfessionalId);

    assertNotNull(result);
    assertEquals(1, result.size());

    }
}

