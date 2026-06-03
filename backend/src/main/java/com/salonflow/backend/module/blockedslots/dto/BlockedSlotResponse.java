package com.salonflow.backend.module.blockedslots.dto;

import com.salonflow.backend.module.blockedslots.BlockedSlots;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.shared.dto.ProfessionalSummary;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockedSlotResponse(

        UUID id,
        ProfessionalSummary professional,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String reason

) {

    public static BlockedSlotResponse from (BlockedSlots blockedSlots){

        return new BlockedSlotResponse(
                blockedSlots.getId(),
                ProfessionalSummary.from(blockedSlots.getProfessional()),
                blockedSlots.getStartAt(),
                blockedSlots.getEndAt(),
                blockedSlots.getReason()
        );
    }

}
