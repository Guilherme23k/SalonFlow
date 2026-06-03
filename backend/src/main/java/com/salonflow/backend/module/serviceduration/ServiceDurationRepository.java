package com.salonflow.backend.module.serviceduration;

import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDurationRepository extends JpaRepository<ServiceDuration, UUID> {

    List<ServiceDuration> findAllByProfessionalIdAndTenantId(UUID professionalId, UUID tenantId);

    Optional<ServiceDuration> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByProfessionalIdAndServiceId(UUID professionalId, UUID serviceId);

    Optional<ServiceDuration> findByProfessionalIdAndServiceIdAndTenantId(
            UUID professionalId, UUID serviceId, UUID tenantId
    );

    List<ServiceDuration> findAllServicesByProfessionalId(UUID professionalId, UUID tenantId);

}
