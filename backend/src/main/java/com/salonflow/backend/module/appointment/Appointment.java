package com.salonflow.backend.module.appointment;

import com.salonflow.backend.module.customer.Customer;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;
import com.salonflow.backend.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    // Snapshot — valor copiado no momento do agendamento
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    // Snapshot — preço copiado no momento do agendamento
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Column
    private String notes;

}
