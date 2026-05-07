package com.salonflow.backend.module.customer;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.customer.dto.CustomerRequest;
import com.salonflow.backend.module.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    @Transactional
    public CustomerResponse findOrCreate(CustomerRequest request){
        UUID tenantId = TenantContext.getCurrentTenant();

        return repository
                .findByPhoneAndTenantId(request.phone(), tenantId)
                .map(existing -> {
                    if (!existing.getName().equals(request.name())){
                        existing.setName(request.name());
                        repository.save(existing);
                    }
                    return CustomerResponse.from(existing);
                })
                .orElseGet(() -> {
                    Customer customer = Customer.builder()
                            .name(request.name())
                            .phone(request.phone())
                            .build();
                    return CustomerResponse.from(repository.save(customer));
                });
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id){
        return repository
                .findByIdAndTenantId(id,TenantContext.getCurrentTenant())
                .map(CustomerResponse::from)
                .orElseThrow(() -> new BusinessException(
                        "Customer not found",
                        HttpStatus.NOT_FOUND
                ));
    }



}
