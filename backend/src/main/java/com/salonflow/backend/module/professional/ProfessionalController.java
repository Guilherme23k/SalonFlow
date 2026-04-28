package com.salonflow.backend.module.professional;

import com.salonflow.backend.module.professional.dto.ProfessionalRequest;
import com.salonflow.backend.module.professional.dto.ProfessionalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/professionals")
@RequiredArgsConstructor
public class ProfessionalController {

    private final ProfessionalService professionalService;

    @PostMapping
    public ResponseEntity<ProfessionalResponse> create(@Valid @RequestBody ProfessionalRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(professionalService.create(request));

    }

    @GetMapping()
    public ResponseEntity<List<ProfessionalResponse>> findAll(){
        return ResponseEntity.ok(professionalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(professionalService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProfessionalResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProfessionalRequest request
    ){
        return ResponseEntity.ok().body(professionalService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactive (@PathVariable UUID id){
        professionalService.deactive(id);
        return ResponseEntity.noContent().build();
    }


}
