package com.salonflow.backend.module.serviceduration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceDurationRepository extends JpaRepository<ServiceDuration, UUID> {

    List<ServiceDuration> findAllByProfessionalIdAndTenantId(UUID professionalId, UUID tenantId);

    Optional<ServiceDuration> findByIdAndTenantId(UUID id, UUID tenantId);

    @Query("SELECT COUNT(sd) > 0 FROM ServiceDuration sd WHERE sd.professional.id = :professionalId AND sd.service.id = :serviceId")
    boolean existsByProfessionalIdAndServiceId(@Param("professionalId") UUID professionalId, @Param("serviceId") UUID serviceId);

    Optional<ServiceDuration> findByProfessionalIdAndServiceIdAndTenantId(
            UUID professionalId, UUID serviceId, UUID tenantId
    );

}
