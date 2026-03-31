package com.salonflow.backend.service.Impl;

import com.salonflow.backend.controller.dtos.professional.ProfessionalCreateDTO;
import com.salonflow.backend.controller.dtos.professional.ProfessionalDTO;
import com.salonflow.backend.controller.dtos.response.ProfessionalResponseDTO;
import com.salonflow.backend.domain.model.Professional;
import com.salonflow.backend.domain.repository.ProfessionalRepository;
import com.salonflow.backend.service.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    public ProfessionalResponseDTO create(@Valid ProfessionalCreateDTO dto) {

        Professional professional = new Professional();
        professional.setName(dto.name());
        professional.setPhone(dto.phone());
        professional.setCommissionPercentage(dto.commisionPercentage());

        Professional savedProfessional = professionalRepository.save(professional);
        return ProfessionalResponseDTO.toResponseDTO(savedProfessional);


    }

    @Override
    public ProfessionalResponseDTO edit(ProfessionalDTO dto) {

        Professional oldProfessional = professionalRepository.findByName(dto.name())
                .orElseThrow(() -> new RuntimeException("Professional Not Found"));

        oldProfessional.setName(dto.name());
        oldProfessional.setPhone(dto.phone());

        Professional updatedProfessional = professionalRepository.save(oldProfessional);

        return ProfessionalResponseDTO.toResponseDTO(updatedProfessional);

    }
}
