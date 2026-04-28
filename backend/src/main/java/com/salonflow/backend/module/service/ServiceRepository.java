package com.salonflow.backend.module.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {

    List<Service> findAllByTenantId (UUID id);
    Optional<Service> findByIdAndTenantId (UUID id, UUID tenantId);

}
