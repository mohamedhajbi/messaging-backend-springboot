package com.test.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @CrossOrigin("*")
    @GetMapping("/getUserInfo")
    public String getUserInfo(Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return "{\"email\": \"" + jwt.getClaims().get("email") + "\"}";
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Vérifiez que le header est de type "Bearer token"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            throw new IllegalArgumentException("Authorization header must be in the format 'Bearer token'");
        }
    }
}