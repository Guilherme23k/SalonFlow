package com.salonflow.backend.module.blockedslots;

import com.salonflow.backend.infra.TenantContext;
import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.module.blockedslots.dto.BlockedSlotRequest;
import com.salonflow.backend.module.blockedslots.dto.BlockedSlotResponse;
import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.professional.ProfessionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockedSlotService {

    private final BlockedSlotsRepository blockedSlotsRepository;
    private final ProfessionalRepository professionalRepository;

    @Transactional
    public BlockedSlotResponse create(BlockedSlotRequest request){
        UUID tenantId = TenantContext.getCurrentTenant();

        if (!request.endAt().isAfter(request.startAt())){
            throw new BusinessException(
                    "The end of blocked slot must be after the beginning",
                    HttpStatus.CONFLICT
            );
        }

        Professional professional = professionalRepository.findByIdAndTenantId(
                request.professionalId(), tenantId
        )
                .orElseThrow(
                        () -> new BusinessException(
                                "Professional not found",
                                HttpStatus.NOT_FOUND
                        )
                );

        List<BlockedSlots> overlapping = blockedSlotsRepository.findOverLapping(
                request.professionalId(), tenantId, request.startAt(), request
                        .endAt()
        );

        if (!overlapping.isEmpty()){
            throw new BusinessException(
                    "There is already a blockage in place for professionals during this period",
                    HttpStatus.CONFLICT
            );
        }

        BlockedSlots blockedSlots = BlockedSlots.builder()
                .professional(professional)
                .startAt(request.startAt())
                .endAt(request.endAt())
                .reason(request.reason())
                .build();

        return BlockedSlotResponse.from(blockedSlotsRepository.save(blockedSlots));

    }

    @Transactional(readOnly = true)
    public List<BlockedSlotResponse> findByProfessionalAndDate(
            UUID professionalId, LocalDate date) {

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return blockedSlotsRepository
                .findByProfessionalAndDate(
                        professionalId, TenantContext.getCurrentTenant(),
                        startOfDay, endOfDay)
                .stream()
                .map(BlockedSlotResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BlockedSlotResponse> findByProfessionalId(UUID professionalId){

        return blockedSlotsRepository.findAllByProfessionalIdAndTenantId(professionalId,
                TenantContext.getCurrentTenant())
                .stream()
                .map(BlockedSlotResponse::from)
                .toList();
    }

    @Transactional
    public void delete(UUID id){
        BlockedSlots blockedSlots = blockedSlotsRepository
                .findByIdAndTenantId(id, TenantContext.getCurrentTenant())
                .orElseThrow(
                        () -> new BusinessException(
                                "Block not found",
                                HttpStatus.NOT_FOUND
                        ));

        blockedSlotsRepository.delete(blockedSlots);
    }


}
