package com.contactmanagementsystem.cms.controller;

import com.contactmanagementsystem.cms.dto.JwtResponse;
import com.contactmanagementsystem.cms.dto.LoginRequest;
import com.contactmanagementsystem.cms.dto.MessageResponse;
import com.contactmanagementsystem.cms.dto.SignupRequest;
import com.contactmanagementsystem.cms.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }


    // In AuthController.java
    @PostMapping("/signup/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerAdmin(signUpRequest));
    }
}