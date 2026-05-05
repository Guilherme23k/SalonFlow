package com.salonflow.backend.module.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByPhoneAndTenantId(String phone, UUID tenantId);
    Optional<Customer> findByIdAndTenantId(UUID id, UUID tenantId);

}
