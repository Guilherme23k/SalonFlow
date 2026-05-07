package com.salonflow.backend.infra.multitenancy;

import com.salonflow.backend.infra.TenantContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-Id";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String tenantHeader = request.getHeader(TENANT_HEADER);

        if (tenantHeader == null || tenantHeader.isBlank()){
            log.warn("Request without X-Tenant-Id: {} {}", request.getMethod(), request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }

        try {
            UUID tenantId = UUID.fromString(tenantHeader);
            TenantContext.setCurrentTenant(tenantId);
            log.debug("Tenant set: {}", tenantId);
            return true;
        } catch (IllegalArgumentException e){
            log.warn("X-Tenant-Id invalid: {}", tenantHeader);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        TenantContext.clear();
        log.debug("TenantContext clean after request");
    }



}
