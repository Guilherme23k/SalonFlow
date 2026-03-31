package com.salonflow.backend.service;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.controller.dtos.professional.ProfessionalDTO;
import com.salonflow.backend.controller.dtos.response.ProfessionalResponseDTO;
import com.salonflow.backend.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessionalService  {

    ProfessionalResponseDTO create(ProfessionalCreateDTO dto);

    ProfessionalResponseDTO edit(ProfessionalDTO dto);

}
