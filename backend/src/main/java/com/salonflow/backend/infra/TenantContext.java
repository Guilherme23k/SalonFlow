package com.salonflow.backend.infra;

import com.salonflow.backend.infra.exception.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class TenantContext {

    private static final ThreadLocal<UUID> currentTenant = new ThreadLocal<>();

    private TenantContext() {}

    public static void setCurrentTenant(UUID tenantId) {
        currentTenant.set(tenantId);
    }

    public static UUID getCurrentTenant(){

        UUID tenantId = currentTenant.get();
        if (tenantId == null){
            throw new BusinessException("Unidentified Tenant on request", HttpStatus.BAD_REQUEST);
        }
        return tenantId;

    }

    public static void clear(){
        currentTenant.remove();
    }


}
