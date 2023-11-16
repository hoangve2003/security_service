package com.example.securityserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.securityserviceapi.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

    boolean existsByName(String name);

    boolean existsByEmail(String email);
}
