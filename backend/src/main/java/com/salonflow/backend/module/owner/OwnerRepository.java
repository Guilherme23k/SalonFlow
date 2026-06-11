package com.salonflow.backend.module.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends JpaRepository<Owner, UUID> {
    Optional<Owner> findByEmail(String email);


    @Query("SELECT COUNT(o) > 0 FROM Owner o JOIN Tenant t ON o.tenantId = t.id WHERE t.slug = :slug")
    boolean existsOwnerByTenantSlug(@Param("slug") String slug);
}
