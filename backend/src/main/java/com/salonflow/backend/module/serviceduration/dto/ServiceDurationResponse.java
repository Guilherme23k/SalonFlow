package com.salonflow.backend.module.serviceduration.dto;

import com.salonflow.backend.module.professional.Professional;
import com.salonflow.backend.module.service.Service;
import com.salonflow.backend.module.serviceduration.ServiceDuration;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceDurationResponse(

        UUID id,
        Professional professional,
        Service service,
        Integer durationMinutes,
        BigDecimal price

) {

        public static ServiceDurationResponse from(ServiceDuration sd){
            return new ServiceDurationResponse(
                  sd.getId(),
                  sd.getProfessional(),
                  sd.getService(),
                  sd.getDurationMinutes(),
                  sd.getPrice()
            );
        }

}
