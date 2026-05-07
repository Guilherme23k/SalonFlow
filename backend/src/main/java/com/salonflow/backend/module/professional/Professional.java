package com.salonflow.backend.module.professional;

import com.salonflow.backend.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "professionals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professional extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column(name = "commission_percentage")
    private Double commissionPercentage;

    @Column(nullable = false)
    private Boolean active;


}
