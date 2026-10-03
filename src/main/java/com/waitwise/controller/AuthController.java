package com.waitwise.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String STAFF_USERNAME = "staff";
    private static final String STAFF_PASSWORD = "NextQ@123";

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        if (STAFF_USERNAME.equals(request.username())
                && STAFF_PASSWORD.equals(request.password())) {

            return ResponseEntity.ok(
                    new LoginResponse(
                            true,
                            "Login successful"
                    )
            );
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new LoginResponse(
                                false,
                                "Invalid username or password"
                        )
                );
    }

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record LoginResponse(
            boolean success,
            String message
    ) {}
}