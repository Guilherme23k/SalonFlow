package com.salonflow.backend.module.serviceduration;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.professional.ProfessionalRepository;
import com.salonflow.backend.module.service.Service;
import com.salonflow.backend.module.service.ServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ServiceDurationRepositoryTest {

    @Autowired
    private ServiceDurationRepository serviceDurationRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    private Professional guilherme;
    private Professional outroProfissional;
    private Service escova;

    @BeforeEach
    void setUp() {
        TenantContext.setCurrentTenant(UUID.randomUUID());

        guilherme = new Professional("Guilherme", "11928374659", 10.00, true);
        guilherme = professionalRepository.save(guilherme);

        outroProfissional = new Professional("Marcos Barber", "11999999999", 15.00, true);
        outroProfissional = professionalRepository.save(outroProfissional);


        escova = new Service("Escova", "Escova e prancha", true);
        escova = serviceRepository.save(escova);


        ServiceDuration vinculoMarcos = new ServiceDuration(outroProfissional, escova, 40, BigDecimal.valueOf(30));
        serviceDurationRepository.save(vinculoMarcos);
    }

    @Test
    @DisplayName("Should return empty list when professional does not perform any service")
    void findNoneServicesByProfessionalId() {

        List<ServiceDuration> result = serviceDurationRepository.findAllServicesByProfessionalId(guilherme.getId(), TenantContext.getCurrentTenant());

        assertNotNull(result, "O retorno não deve ser null, mas uma lista vazia");

        assertEquals(0, result.size(), "Bug detectado: o sistema exibiu serviços que o profissional selecionado não faz");

    }

    @Test
    @DisplayName("Should return list whit the entiry services of a professional")
    void findAllServicesByProfessionalId(){

        List<ServiceDuration> result = serviceDurationRepository.findAllServicesByProfessionalId(outroProfissional.getId(), TenantContext.getCurrentTenant());

        assertNotNull(result, "O retorno não deve ser null, tem que ser uma lista com 1 entidade");

        assertEquals(1, result.size(), "Bug: não está pegando os valores certos");

    }
}