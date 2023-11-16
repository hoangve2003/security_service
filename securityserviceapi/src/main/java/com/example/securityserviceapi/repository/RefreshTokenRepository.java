package com.example.securityserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.securityserviceapi.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByToken(String token);
}
