package com.salonflow.backend.module.service.dto;

import com.salonflow.backend.module.service.Service;

import java.util.UUID;

public record ServiceResponse(
        UUID id,
        String name,
        String description,
        Boolean active
) {

    public static ServiceResponse from (Service service){
        return new ServiceResponse(
                service.getId(),
                service.getName(),
                service.getDescription(),
                service.getActive()
        );
    }

}
