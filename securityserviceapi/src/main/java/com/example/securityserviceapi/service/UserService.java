package com.example.securityserviceapi.service;

import org.springframework.http.ResponseEntity;
import com.example.securityserviceapi.entity.UserInfo;

public interface UserService {
    ResponseEntity<UserInfo> addUser(UserInfo userInfo);
}
