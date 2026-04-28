package com.salonflow.backend.module.professional;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.professional.dto.ProfessionalRequest;
import com.salonflow.backend.module.professional.dto.ProfessionalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository repository;

    @Transactional
    public ProfessionalResponse create (ProfessionalRequest request){

        Professional professional = Professional.builder()
                .name(request.name())
                .phone(request.phone())
                .commissionPercentage(request.commissionPercentage())
                .active(true)
                .build();

        return ProfessionalResponse.from(repository.save(professional));
    }

    @Transactional(readOnly = true)
    public List<ProfessionalResponse> findAll(){
        return repository.findAllByTenantId(
                TenantContext.getCurrentTenant())
                .stream()
                .map(ProfessionalResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessionalResponse findById(UUID id){
        return repository.findByIdAndTenantId(
                id, TenantContext.getCurrentTenant())
                .map(ProfessionalResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Professional not found",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public ProfessionalResponse update(UUID id, ProfessionalRequest request){

        Professional professional = repository.findByIdAndTenantId(
                id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new BusinessException(
                        "Professional not found",
                        HttpStatus.NOT_FOUND
                ));

        professional.setName(request.name());
        professional.setPhone(request.phone());
        professional.setCommissionPercentage(request.commissionPercentage());

        return ProfessionalResponse.from(repository.save(professional));

    }

    @Transactional
    public ProfessionalResponse deactive (UUID id){
        Professional professional = repository.findByIdAndTenantId(
                id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new BusinessException(
                        "Professional not found",
                        HttpStatus.NOT_FOUND
                ));

        professional.setActive(false);

        return ProfessionalResponse.from(repository.save(professional));
    }

}
