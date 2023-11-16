package com.example.securityserviceapi.service.impl;

import com.example.securityserviceapi.exception.ExpiredRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.securityserviceapi.dto.AuthRequest;
import com.example.securityserviceapi.dto.JwtResponse;
import com.example.securityserviceapi.dto.RefreshTokenRequest;
import com.example.securityserviceapi.entity.RefreshToken;
import com.example.securityserviceapi.repository.RefreshTokenRepository;
import com.example.securityserviceapi.repository.UserInfoRepository;
import com.example.securityserviceapi.service.JwtService;
import com.example.securityserviceapi.service.RefreshTokenService;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserInfoRepository userInfoRepository;

    /**
     * Creates a new refresh token for the specified username.
     *
     * @param username The username for which to create the refresh token.
     * @return The created refresh token.
     */
    private RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userInfoRepository.findByName(username).orElse(null))
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // 10 minutes
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Finds a refresh token by its token string.
     *
     * @param token The token string.
     * @return Optional containing the refresh token, or empty if not found.
     */
    private Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verifies if a refresh token has expired.
     *
     * @param token The refresh token to verify.
     * @return The verified refresh token.
     * @throws ExpiredRefreshTokenException If the token has expired.
     */
    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ExpiredRefreshTokenException(token.getToken());
        }
        return token;
    }

    /**
     * Refreshes the access token using a valid refresh token.
     *
     * @param refreshTokenRequest The request containing the refresh token.
     * @return ResponseEntity containing the refreshed JWT response.
     * @throws RuntimeException If the refresh token is not in the database.
     */
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return this.findByToken(refreshTokenRequest.getToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo.getName());
                    return ResponseEntity.ok(JwtResponse.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequest.getToken())
                            .build());
                }).orElseThrow(() -> new RuntimeException(
                        "Refresh token is not in the database!"));
    }

    /**
     * Authenticates a user and generates an access token along with a refresh token.
     *
     * @param authRequest The authentication request containing the username and password.
     * @return ResponseEntity containing the JWT response.
     * @throws UsernameNotFoundException If the user is not found or the authentication fails.
     */
    public ResponseEntity<JwtResponse> authenticateAndGetToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = this.createRefreshToken(authRequest.getUsername());
            return ResponseEntity.ok(JwtResponse.builder()
                    .accessToken(jwtService.generateToken(authRequest.getUsername()))
                    .token(refreshToken.getToken())
                    .build());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

}
