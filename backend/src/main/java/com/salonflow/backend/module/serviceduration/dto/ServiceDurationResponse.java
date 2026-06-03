package com.salonflow.backend.module.serviceduration.dto;

import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;
import com.salonflow.backend.module.serviceduration.ServiceDuration;
import com.salonflow.backend.shared.dto.ProfessionalSummary;
import com.salonflow.backend.shared.dto.ServiceSummary;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceDurationResponse(

        UUID id,
        ProfessionalSummary professional,
        ServiceSummary service,
        Integer durationMinutes,
        BigDecimal price,

        boolean active

) {

        public static ServiceDurationResponse from(ServiceDuration sd){
            return new ServiceDurationResponse(
                  sd.getId(),
                  ProfessionalSummary.from(sd.getProfessional()),
                  ServiceSummary.from(sd.getService()),
                  sd.getDurationMinutes(),
                  sd.getPrice(),
                  sd.getService().getActive()
            );
        }

}
