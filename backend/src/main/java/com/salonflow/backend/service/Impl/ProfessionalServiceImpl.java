package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.controller.dtos.response.ProfessionalResponseDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.service.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public ProfessionalResponseDTO create(@Valid ProfessionalCreateDTO dto) {


        Professional professional = new Professional();
        professional.setName(dto.name());
        professional.setPhone(dto.phone());
        professional.setCommissionPercentage(dto.commisionPercentage());

        Professional savedProfessional = professionalRepository.save(professional);
        return ProfessionalResponseDTO.toResponseDTO(savedProfessional);


    }
}
