package com.salonflow.backend.domain.repository;

import com.salonflow.backend.domain.model.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfessionalRepository extends JpaRepository<Professional, UUID> {

    @Query(value = "SELECT n FROM Professional n WHERE n.name = :name")
    Optional<Professional> findByName(@Param("name") String name);

}
