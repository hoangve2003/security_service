package com.example.securityserviceapi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.securityserviceapi.dto.AuthRequest;
import com.example.securityserviceapi.dto.JwtResponse;
import com.example.securityserviceapi.dto.RefreshTokenRequest;


public interface RefreshTokenService {
    ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest);

    ResponseEntity<JwtResponse> authenticateAndGetToken(AuthRequest authRequest);

}
