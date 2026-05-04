package com.salonflow.backend.module.blockedslots.dto;

import com.salonflow.backend.module.blockedslots.BlockedSlots;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedSlotResponse(

        UUID id,
        UUID professionalId,
        String professionalName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String reason

) {

    public static BlockedSlotResponse from (BlockedSlots blockedSlots){

        return new BlockedSlotResponse(
                blockedSlots.getId(),
                blockedSlots.getProfessional().getId(),
                blockedSlots.getProfessional().getName(),
                blockedSlots.getStartAt(),
                blockedSlots.getEndAt(),
                blockedSlots.getReason()
        );
    }

}
