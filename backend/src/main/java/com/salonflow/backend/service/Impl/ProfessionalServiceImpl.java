package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.service.ProfessionalService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public Professional create(ProfessionalCreateDTO dto) {

        Professional professional = new Professional();
        BeanUtils.copyProperties(dto,professional);
        return professionalRepository.save(professional);

    }
}
