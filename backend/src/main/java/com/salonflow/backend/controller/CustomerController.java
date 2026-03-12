package com.salonflow.backend.controller;

import com.salonflow.backend.controller.dtos.CustomerCreateDTO;
import com.salonflow.backend.controller.dtos.CustomerDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.service.CustomerService;
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

    @PostMapping
    public ResponseEntity<CustomerDTO> findOrCreateCustomerByPhone(@RequestBody @Valid CustomerCreateDTO dto){


        CustomerDTO customerDTO = customerService.findOrCreateByTelefone(dto);


        return ResponseEntity.ok().body(customerDTO);


    }


}
