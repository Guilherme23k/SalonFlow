package com.salonflow.backend.domain.model;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "professionals")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToMany
    private List<ProfessionalServices> services;

    private Double commissionPercentage;

    @BRPhoneNumber
    private String phone;

    private Boolean active;

}
