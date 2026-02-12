package com.salonflow.backend.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "professional_services")
public class ProfessionalServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    @ManyToOne
    private Professional professional;

}
