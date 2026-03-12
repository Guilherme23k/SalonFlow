package com.salonflow.backend.domain.repository;

import com.salonflow.backend.domain.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {



}
