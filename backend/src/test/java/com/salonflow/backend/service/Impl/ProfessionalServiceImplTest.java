package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.controller.dtos.response.ProfessionalResponseDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfessionalServiceImplTest {

    @Mock
    private ProfessionalRepository professionalRepository;

    @InjectMocks
    private ProfessionalServiceImpl professionalService;



    @Test
    public void shouldCreateProfessional(){

        ProfessionalCreateDTO dto = new ProfessionalCreateDTO("Patricia", "11954782365", 10.0);

        Professional professional = new Professional();
        professional.setId(UUID.randomUUID());
        professional.setName(dto.name());
        professional.setPhone(dto.phone());
        professional.setServices(new ArrayList<>());
        professional.setCommissionPercentage(dto.commisionPercentage());

        when(professionalRepository.save(any(Professional.class))).thenReturn(professional);

        ProfessionalResponseDTO results =professionalService.create(dto);


        assertNotNull(results);
        assertEquals(professional.getName(), results.name());
        assertEquals(professional.getPhone(), results.phone());
    }

    @Test
    public void shouldCreateProfessionalWithCommission(){

        ProfessionalCreateDTO dto = new ProfessionalCreateDTO("Patricia", "11914521452", 10.0);

        Professional professional = new Professional();
        professional.setName(dto.name());
        professional.setPhone(dto.phone());
        professional.setCommissionPercentage(dto.commisionPercentage());

        when(professionalRepository.save(any(Professional.class))).thenReturn(professional);

        professionalService.create(dto);

        verify(professionalRepository).save(argThat(p ->
                Double.valueOf(10.0).equals(p.getCommissionPercentage()) &&
                p.getName().equals("Patricia")
        ));

    }

    @Test
    public void shouldEditProfessional(){



    }

}