package com.example.securityserviceapi.service.impl;

import com.example.securityserviceapi.entity.UserInfo;
import com.example.securityserviceapi.exception.ExistsException;
import com.example.securityserviceapi.repository.UserInfoRepository;
import com.example.securityserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserInfoRepository repository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Adds a new user to the system. Encrypts the user's password before saving to the repository.
     *
     * @param userInfo The user information to be added.
     * @return ResponseEntity containing the added user information.
     */
    public ResponseEntity<UserInfo> addUser(UserInfo userInfo) {
        if (repository.existsByName(userInfo.getName())) {
            throw new ExistsException("Name is duplicated: " + userInfo.getName());
        }
        if (repository.existsByEmail(userInfo.getEmail())) {
            throw new ExistsException("Email is duplicated: " + userInfo.getEmail());
        }
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return ResponseEntity.ok(userInfo);
    }
}
