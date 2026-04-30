package com.salonflow.backend.module.service;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.service.dto.ServiceRequest;
import com.salonflow.backend.module.service.dto.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository repository;

    @Transactional
    public ServiceResponse create (ServiceRequest request){

        com.salonflow.backend.module.service.Service service =
                com.salonflow.backend.module.service.Service.builder()
                .name(request.name())
                .description(request.description())
                .active(true)
                .build();

        return ServiceResponse.from(repository.save(service));
    }

    @Transactional
    public List<ServiceResponse> findAll (){

        return repository.findAllByTenantId(
                TenantContext.getCurrentTenant())
                .stream()
                .map(ServiceResponse::from)
                .toList();

    }

    @Transactional
    public ServiceResponse findById (UUID id){

        return repository.findByIdAndTenantId(
                id, TenantContext.getCurrentTenant())
                .map(ServiceResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Service not found",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional
    public ServiceResponse update (UUID id, ServiceRequest request){

        com.salonflow.backend.module.service.Service updateService = repository.findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(() -> new BusinessException(
                        "Service not found",
                        HttpStatus.NOT_FOUND
                ));

        updateService.setName(request.name());
        updateService.setDescription(request.description());

        return ServiceResponse.from(repository.save(updateService));
    }

    @Transactional
    public void deactive (UUID id){
        com.salonflow.backend.module.service.Service service =
                repository.findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                        .orElseThrow(() -> new BusinessException(
                                "Service not found",
                                HttpStatus.NOT_FOUND
                        ));

        service.setActive(false);
        repository.save(service);
    }

}
