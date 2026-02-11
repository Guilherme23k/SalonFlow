package com.salonflow.backend.domain.model;

import com.salonflow.backend.domain.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime scheduleTime;

    @ManyToOne
    @JoinColumn(name = "customers_id")
    private Customer customer;

    @OneToMany(mappedBy = "schedule")
    private List<AppointmentItem> items;

    private Status status;
}
