package com.salonflow.backend.module.appointment;

import com.salonflow.backend.module.appointment.dto.AppointmentRequest;
import com.salonflow.backend.module.appointment.dto.AppointmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(
            @Valid
            @RequestBody
            AppointmentRequest request
    ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<AppointmentResponse>> findByProfessional(
            @PathVariable UUID professionalId){
        return ResponseEntity.ok(service.findByProfessional(professionalId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> findById (@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancel(@PathVariable UUID id){
        return ResponseEntity.ok(service.cancel(id));
    }


}
