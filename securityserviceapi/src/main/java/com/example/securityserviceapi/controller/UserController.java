package com.example.securityserviceapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.securityserviceapi.dto.AuthRequest;
import com.example.securityserviceapi.dto.JwtResponse;
import com.example.securityserviceapi.dto.RefreshTokenRequest;
import com.example.securityserviceapi.entity.UserInfo;
import com.example.securityserviceapi.service.RefreshTokenService;
import com.example.securityserviceapi.service.UserService;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {


    private final UserService userService;

    private final RefreshTokenService refreshTokenService;


    /**
     * Controller method to handle user registration.
     *
     * @param userInfo The user information to be registered.
     * @return ResponseEntity containing the registered user information.
     */
    @PostMapping("/signUp")
    public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo userInfo) {
        return userService.addUser(userInfo);
    }

    /**
     * Controller method to handle user authentication and token retrieval.
     *
     * @param authRequest The authentication request containing user credentials.
     * @return ResponseEntity containing the JWT response.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return refreshTokenService.authenticateAndGetToken(authRequest);
    }

    /**
     * Controller method to handle token refresh.
     *
     * @param refreshTokenRequest The refresh token request.
     * @return ResponseEntity containing the refreshed JWT response.
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }



}
