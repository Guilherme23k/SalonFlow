package com.salonflow.backend.module.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Optional<Appointment> findByIdAndTenantId(UUID id, UUID tenantId);
    List<Appointment> findAllByTenantIdOrderByScheduledAtDesc(UUID tenantId);

    List<Appointment> findAllByProfessionalIdAndTenantIdOrderByScheduledAtAsc(
            UUID professionalId, UUID tenantId
    );

    @Query(value = """
            SELECT * FROM appointments
            WHERE professional_id = :professionalId
            AND tenantId = :tenantId
            AND status = 'CONFIRMED'
            AND scheduled_at < :end
            AND scheduled_at + (duration_minutes * INTERVAL '1 minute') > :start
            """, nativeQuery = true)
    List<Appointment> findConflicting(
            @Param("professionalId") UUID professionalId,
            @Param("tenantId") UUID tenantId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
