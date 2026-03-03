package com.salonflow.backend.controller;

import com.salonflow.backend.controller.dtos.CustomerDTO;
import com.salonflow.backend.controller.dtos.response.CustomerResponseDTO;
import com.salonflow.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
