package com.salonflow.backend.domain.repository;

import com.salonflow.backend.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {


    @Query(value = "SELECT c FROM Customer c WHERE c.phone = :phone")
    List<Customer> findByPhone(@Param("telefone") String phone);

    @Query(value = "SELECT c FROM Customer c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(String name);

    Boolean existsByPhone(String phone);

}
