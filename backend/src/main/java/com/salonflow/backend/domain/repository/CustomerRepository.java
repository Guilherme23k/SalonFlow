package com.salonflow.backend.domain.repository;

import com.salonflow.backend.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerRepository, UUID> {


    @Query(value = "SELECT c FROM Customer c WHERE c.telefone = :telefone")
    List<Customer> findByTelefone(@Param("telefone") String telefone);

}
