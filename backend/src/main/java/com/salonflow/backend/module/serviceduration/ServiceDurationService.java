package com.salonflow.backend.module.serviceduration;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.professional.ProfessionalRepository;
import com.salonflow.backend.module.service.ServiceRepository;
import com.salonflow.backend.module.serviceduration.dto.ServiceDurationRequest;
import com.salonflow.backend.module.serviceduration.dto.ServiceDurationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceDurationService {

    private final ServiceDurationRepository serviceDurationRepository;
    private final ServiceRepository serviceRepository;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public ServiceDurationResponse create(ServiceDurationRequest request){

        UUID tenantId = TenantContext.getCurrentTenant();

        if (serviceDurationRepository.existsByProfessionalIdAndServiceId(
                request.professionalId(), request.serviceId()
        )){
            throw new BusinessException(
                    "This Professional already have a duration registered for this Service",
                    HttpStatus.CONFLICT
            );
        }


        Professional professional = professionalRepository
                .findByIdAndTenantId(request.professionalId(), tenantId)
                .orElseThrow(() -> new BusinessException(
                        "Profissional not found",
                        HttpStatus.NOT_FOUND
                ));

        com.salonflow.backend.module.service.Service service = serviceRepository
                .findByIdAndTenantId(request.serviceId(), tenantId)
                .orElseThrow(() -> new BusinessException(
                        "Serviço not found",
                        HttpStatus.NOT_FOUND
                ));

        ServiceDuration sd = ServiceDuration.builder()
                .professional(professional)
                .service(service)
                .durationMinutes(request.durationMinutes())
                .price(request.price())
                .build();

        return ServiceDurationResponse.from(serviceDurationRepository.save(sd));
    }

    @Transactional(readOnly = true)
    public List<ServiceDurationResponse> findByProfessionalId(UUID professionalId){
        return serviceDurationRepository
                .findAllByProfessionalIdAndTenantId(professionalId, TenantContext.getCurrentTenant())
                .stream()
                .map(ServiceDurationResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceDurationResponse findByProfessionalAndService(
            UUID professionalId, UUID serviceId) {

        return serviceDurationRepository
                .findByProfessionalIdAndServiceIdAndTenantId(
                        professionalId, serviceId, TenantContext.getCurrentTenant())
                .map(ServiceDurationResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Este profissional não realiza o serviço solicitado",
                        HttpStatus.NOT_FOUND
                ));
    }

    @Transactional(readOnly = true)
    public ServiceDurationResponse update (UUID id, ServiceDurationRequest request){

        ServiceDuration sd = serviceDurationRepository
                .findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow( () -> new BusinessException(
                        "Bond not found",
                        HttpStatus.NOT_FOUND
                ) );

        sd.setDurationMinutes(request.durationMinutes());
        sd.setPrice(request.price());

        return ServiceDurationResponse.from(serviceDurationRepository.save(sd));
    }

    @Transactional()
    public void delete (UUID id){

        ServiceDuration sd = serviceDurationRepository.findByIdAndTenantId(
                id, TenantContext.getCurrentTenant()
        )
                .orElseThrow(() -> new BusinessException(
                        "Bond not found",
                        HttpStatus.NOT_FOUND
                ));

        serviceDurationRepository.delete(sd);

    }


}
