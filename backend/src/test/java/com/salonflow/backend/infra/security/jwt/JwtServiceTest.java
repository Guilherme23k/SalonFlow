package com.salonflow.backend.infra.security.jwt;

import com.salonflow.backend.module.owner.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Ref;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    @Mock
    private Owner owner;

    private final String testSecretKey = "46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5";

    @BeforeEach
    void setup(){
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secretKey", testSecretKey);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 60000L);
    }

    @Test
    @DisplayName("T-004: Must generate valid jwt token with correct claims")
    void mustGenerateTokenWithSucess(){
        UUID userId = UUID.randomUUID();
        String tenantId = UUID.randomUUID().toString();

        when(owner.getId()).thenReturn(userId);
        when(owner.getTenantId()).thenReturn(UUID.fromString(tenantId));
        when(owner.getEmail()).thenReturn("owner@email.com");

        String token = jwtService.generateToken(owner);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        assertEquals(tenantId, jwtService.extractTenantId(token));

    }


}