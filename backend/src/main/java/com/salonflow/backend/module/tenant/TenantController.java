package com.salonflow.backend.module.tenant;

import com.salonflow.backend.module.tenant.dto.TenantRequest;
import com.salonflow.backend.module.tenant.dto.TenantResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantResponse> create (@Valid @RequestBody TenantRequest request){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                tenantService.create(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantResponse> findById (@PathVariable UUID id){
        return ResponseEntity.ok(tenantService.findById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<TenantResponse> findBySlug (@PathVariable String slug){
        return ResponseEntity.ok(tenantService.findBySlug(slug));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TenantResponse> update (
            @PathVariable UUID id,
            @Valid @RequestBody TenantRequest request
    ){
        return ResponseEntity.ok(tenantService.update(id, request));
    }


}
