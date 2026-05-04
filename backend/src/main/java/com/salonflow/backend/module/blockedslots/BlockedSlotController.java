package com.salonflow.backend.module.blockedslots;

import com.salonflow.backend.module.blockedslots.dto.BlockedSlotRequest;
import com.salonflow.backend.module.blockedslots.dto.BlockedSlotResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/blocked-slots")
@RequiredArgsConstructor
public class BlockedSlotController {

    private final BlockedSlotService blockedSlotService;

    @PostMapping
    public ResponseEntity<BlockedSlotResponse> create(@Valid @RequestBody BlockedSlotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(blockedSlotService.create(request));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<BlockedSlotResponse>> findByProfessional(
            @PathVariable UUID professionalId) {
        return ResponseEntity.ok(blockedSlotService.findByProfessionalId(professionalId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        blockedSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
