package com.salonflow.backend.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "appointmentItem")
public class AppointmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Schedule schedule;

    @ManyToOne
    private ProfessionalServices professionalServices;

    private Double appliedPrice;

}
