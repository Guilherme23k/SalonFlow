package com.salonflow.backend.service;

import com.salonflow.backend.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessionalService extends JpaRepository<UUID, Professional> {
}
