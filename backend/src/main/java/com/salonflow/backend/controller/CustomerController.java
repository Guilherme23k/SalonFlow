package com.salonflow.backend.controller;

import com.salonflow.backend.controller.dtos.customer.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{phone}")
    public ResponseEntity<CustomerResponseDTO> findByPhoneNumber(@PathVariable String phone){
        CustomerResponseDTO customer = customerService.findCustomerByPhone(phone);

        return ResponseEntity.ok(customer);
    }

    @Operation(
            summary = "Find or register a customer",
            description = "Register a customer on database or return a customer if already exists in database."
    )
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> findOrCreateCustomerByPhone(@RequestBody @Valid CustomerCreateDTO dto){


        return ResponseEntity.ok(
                CustomerResponseDTO.toDTO(customerService.findOrCreateByPhone(dto))
        );

    }


}
