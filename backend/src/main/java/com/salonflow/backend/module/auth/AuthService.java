package com.salonflow.backend.module.auth;

import com.salonflow.backend.infra.exception.BusinessException;
import com.salonflow.backend.infra.security.jwt.JwtService;
import com.salonflow.backend.module.auth.dto.AuthResponse;
import com.salonflow.backend.module.auth.dto.LoginRequest;
import com.salonflow.backend.module.auth.dto.RegisterRequest;
import com.salonflow.backend.module.owner.Owner;
import com.salonflow.backend.module.owner.OwnerRepository;
import com.salonflow.backend.module.tenant.TenantService;
import com.salonflow.backend.module.tenant.dto.TenantResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final TenantService tenantService;
    private final OwnerRepository ownerRepository;

    private BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(TenantService tenantService, OwnerRepository ownerRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService){
        this.tenantService = tenantService;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void register(RegisterRequest request){

        if (!tenantService.existsBySlug(request.tenantSlug()) || ownerRepository.existsOwnerByTenantSlug(request.tenantSlug())){
            throw new BusinessException("Tenant not found or already registered", HttpStatus.BAD_REQUEST);
        }

        TenantResponse tenant = tenantService.findBySlug(request.tenantSlug());

        String encryptedPassword = passwordEncoder.encode(request.password());

        Owner owner = new Owner(
                UUID.randomUUID(),
                tenant.id(),
                request.name(),
                request.email(),
                encryptedPassword,
                LocalDateTime.now(),
                LocalDateTime.now());

        ownerRepository.save(owner);
    }


    public AuthResponse login(LoginRequest loginRequest){
        Owner owner = ownerRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.password(), owner.getPassword())){
            throw new RuntimeException("Invalid Credentials");
        }

        String token = jwtService.generateToken(owner);
        return new AuthResponse(token, owner.getTenantId(), owner.getName());
    }

}
