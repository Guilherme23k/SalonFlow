package com.salonflow.backend.domain.model;

import com.salonflow.backend.validations.interfaces.BRPhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @BRPhoneNumber
    private String phone;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;


}
