package com.salonflow.backend.module.serviceduration;

import com.salonflow.backend.module.serviceduration.dto.ServiceDurationRequest;
import com.salonflow.backend.module.serviceduration.dto.ServiceDurationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service-durations")
@RequiredArgsConstructor
public class ServiceDurationController {

    private final ServiceDurationService service;

    @PostMapping
    public ResponseEntity<ServiceDurationResponse> create (
            @Valid
            @RequestBody
            ServiceDurationRequest request
    ){

        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));

    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<ServiceDurationResponse>> findByProfessional(
            @PathVariable UUID professionalId
            ){
        return ResponseEntity.ok(service.findByProfessionalId(professionalId));
    }

    @GetMapping("/professional/{professionalId}/service/{serviceId}")
    public ResponseEntity<ServiceDurationResponse> findByIdAndService(
            @PathVariable UUID professionalId,
            @PathVariable UUID serviceId
    ){
        return ResponseEntity.ok(
            service.findByProfessionalAndService(professionalId,serviceId)
        );
    }

    @GetMapping("/professional/{professionalId}/services")
    public ResponseEntity<List<ServiceDurationResponse>> findAllServicesByProfesisonalId(
            @PathVariable UUID professionalId
    ){
        return ResponseEntity.ok(
                service.findAllServicesByProfessionalId(professionalId)
        );
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ServiceDurationResponse> update(

            @PathVariable UUID id,
            @Valid
            @RequestBody ServiceDurationRequest request

    ){

        return ResponseEntity.ok(service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
