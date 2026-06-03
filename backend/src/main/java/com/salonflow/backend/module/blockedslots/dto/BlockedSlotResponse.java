package com.salonflow.backend.module.blockedslots.dto;

import com.salonflow.backend.module.blockedslots.BlockedSlots;
import com.salonflow.backend.module.professional.Professional;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedSlotResponse(

        UUID id,
        Professional professional,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String reason

) {

    public static BlockedSlotResponse from (BlockedSlots blockedSlots){

        return new BlockedSlotResponse(
                blockedSlots.getId(),
                blockedSlots.getProfessional(),
                blockedSlots.getStartAt(),
                blockedSlots.getEndAt(),
                blockedSlots.getReason()
        );
    }

}
