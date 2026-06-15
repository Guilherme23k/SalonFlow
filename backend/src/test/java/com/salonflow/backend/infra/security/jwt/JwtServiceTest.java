package com.salonflow.backend.infra.security.jwt;

import com.salonflow.backend.module.owner.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;
    @Mock
    private Owner owner;

    @BeforeEach
    void setup(){
        jwtService = new JwtService();

        String testSecretKey = "46070d4bf934fb0d4b06d9e2c46e346944e322444900a435d7d9a95e6d7435f5";
        ReflectionTestUtils.setField(jwtService, "key", testSecretKey);
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

    @Test
    @DisplayName("Negative path: token with tenant changed")
    void mustReturnFalseWhenTokenIsInvalid(){
        UUID userId = UUID.randomUUID();
        String tenantId = UUID.randomUUID().toString();

        when(owner.getTenantId()).thenReturn(UUID.fromString(tenantId));
        when(owner.getId()).thenReturn(userId);
        when(owner.getEmail()).thenReturn("owner@email.com");

        String validToken = jwtService.generateToken(owner);

        String tokenInvalid = "eyJhbGciOiJIUzI1NiJ9.eyJ0ZW5hbnRJZCI6InRlbmFudC1hYmMtMTIzIiwidXNlcklkIjo0Miwic3ViIjoib3duZXJAZW1haWwuY29tIiwiaWF0IjoxNzgxMTEwNDAwLCJleHAiOjE3ODExOTY4MDB9.S3Y4bVJtc2VjcmV0S2V5Rm9ySFMyNTZTaWduaW5nMTIzNDU2Nzg";


        boolean invalidToken = jwtService.isTokenValid(tokenInvalid);
        boolean validTokenProof = jwtService.isTokenValid(validToken);

        assertFalse(invalidToken);
        assertTrue(validTokenProof);
    }


}