package com.salonflow.backend.module.tenant;

import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.tenant.dto.TenantRequest;
import com.salonflow.backend.module.tenant.dto.TenantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository repository;

    @Transactional
    public TenantResponse create(TenantRequest request){
        if (repository.existsBySlug(request.slug())) {
            throw new BusinessException(
                    "Slug '" + request.slug() + "' already exists",
                    HttpStatus.CONFLICT
            );
        }
            Tenant tenant = Tenant.builder()
                    .name(request.name())
                    .slug(request.slug())
                    .openingTime(request.openingTime())
                    .closingTime(request.closingTime())
                    .active(true)
                    .build();

        return TenantResponse.from(repository.save(tenant));
    }

    @Transactional(readOnly = true)
    public TenantResponse findById(UUID id){
        return repository.findById(id)
                .map(TenantResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Tenant not found",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional(readOnly = true)
    public TenantResponse findBySlug(String slug){
        return repository.findBySlug(slug)
                .map(TenantResponse::from)(
                .orElseThrow(() -> new BusinessException(
                        "Tenant not found",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public TenantResponse update(UUID id, TenantRequest request){
        Tenant tenant = repository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Tenant not found",
                        HttpStatus.NOT_FOUND
                ));

        tenant.setName(request.name());
        tenant.setOpeningTime(request.openingTime());
        tenant.setClosingTime(request.closingTime());

        return TenantResponse.from(repository.save(tenant));
    }


}
