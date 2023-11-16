package com.example.securityserviceapi.exception;

public class ExpiredRefreshTokenException extends RuntimeException {

    public ExpiredRefreshTokenException(String token) {
        super(token + " Refresh token was expired. Please make a new signin request");
    }
}

