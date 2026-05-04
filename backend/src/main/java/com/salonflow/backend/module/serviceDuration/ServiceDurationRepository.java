package com.salonflow.backend.module.serviceDuration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDurationRepository extends JpaRepository<ServiceDuration, UUID> {

    List<ServiceDuration> findAllByProfessionalIdAndTenantId(UUID professionalId, UUID tenantId);

    Optional<ServiceDuration> findByIdAndTenantId(UUID id, UUID tenantId);

    boolean existsByProfessionalAndServiceId(UUID professionalId, UUID serviceId);

}
