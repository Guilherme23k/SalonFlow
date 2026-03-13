package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
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
        professional.setName("Patricia");
        professional.setPhone("11947584930");
        professional.setServices(new ArrayList<>());
        professional.setCommissionPercentage(10.0);

        when(professionalRepository.save(any(Professional.class))).thenReturn(professional);

        ProfessionalCreateDTO results = ProfessionalCreateDTO.toDTO(professionalService.create(dto));



    }

}