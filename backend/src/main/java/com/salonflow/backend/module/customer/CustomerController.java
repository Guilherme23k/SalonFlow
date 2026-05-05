package com.salonflow.backend.module.customer;

import com.salonflow.backend.module.customer.dto.CustomerRequest;
import com.salonflow.backend.module.customer.dto.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create (
            @Valid
            @RequestBody
            CustomerRequest request
    ){
        return ResponseEntity.ok(service.findOrCreate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable UUID id
            ){
        return ResponseEntity.ok(service.findById(id));
    }

}
