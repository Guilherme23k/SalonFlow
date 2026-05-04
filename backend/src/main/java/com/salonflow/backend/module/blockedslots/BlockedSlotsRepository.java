package com.salonflow.backend.module.blockedslots;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlockedSlotsRepository extends JpaRepository<BlockedSlots, UUID> {

    Optional<BlockedSlots> findByIdAndTenantId(UUID id, UUID tenantId);

    List<BlockedSlots> findAllByProfessionalIdAndTenantId(UUID professionalId, UUID tenantId);

    @Query("""
            SELECT b FROM BlockedSlot b
            WHERE b.professional.id = :professionalId
            AND b.tenantId = :tenantId
            AND b.startAt < :end
            AND b.endAt > :start
            """)
    List<BlockedSlots> findOverLapping(
            @Param("professionalId") UUID professionalId,
            @Param("tenantId") UUID tenantId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
