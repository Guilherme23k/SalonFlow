package com.salonflow.backend.module.service;

import com.salonflow.backend.module.service.dto.ServiceRequest;
import com.salonflow.backend.module.service.dto.ServiceResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/services")
public class ServiceController {

    private final ServiceService service;

    @PostMapping
    public ResponseEntity<ServiceResponse> create (@Valid @RequestBody ServiceRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> findById(
            @PathVariable
            UUID id
    ){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ServiceResponse> update (
            @Valid
            @RequestBody ServiceRequest request,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactive (@PathVariable UUID id){
        service.deactive(id);
        return ResponseEntity.noContent().build();
    }

}
