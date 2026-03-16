package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.service.ProfessionalService;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public Professional create(ProfessionalCreateDTO dto) {

        Professional professional = new Professional();
        professional.setName(dto.name());
        professional.setPhone(dto.phone());
        professional.setCommissionPercentage(dto.commisionPercentage());

        if (dto.name().isEmpty() || dto.phone().isEmpty() || dto.commisionPercentage().isNaN()){
            throw new RuntimeException("An argument not found");
        }

        return professionalRepository.save(professional);

    }
}
