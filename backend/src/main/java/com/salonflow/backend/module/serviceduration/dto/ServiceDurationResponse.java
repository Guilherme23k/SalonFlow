package com.salonflow.backend.module.serviceduration.dto;

import com.salonflow.backend.module.serviceduration.ServiceDuration;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceDurationResponse(

        UUID id,
        UUID professionalId,
        String professionalName,
        UUID serviceId,
        String serviceName,
        String serviceDescription,
        Integer durationMinutes,
        BigDecimal price

) {

        public static ServiceDurationResponse from(ServiceDuration sd){
            return new ServiceDurationResponse(
                  sd.getId(),
                  sd.getProfessional().getId(),
                  sd.getProfessional().getName(),
                  sd.getService().getId(),
                  sd.getService().getName(),
                  sd.getService().getDescription(),
                  sd.getDurationMinutes(),
                  sd.getPrice()
            );
        }

}
