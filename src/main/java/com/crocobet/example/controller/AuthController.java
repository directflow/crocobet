package com.crocobet.example.controller;

import com.crocobet.example.logging.Loggable;
import com.crocobet.example.model.jwt.JwtAuthenticationRequest;
import com.crocobet.example.model.jwt.JwtAuthenticationResponse;
import com.crocobet.example.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Login controller")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Loggable
    @PostMapping("")
    @Operation(summary = "Login with username and password")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody @Valid JwtAuthenticationRequest jwtAuthenticationRequest) throws UsernameNotFoundException {
        return ResponseEntity.ok(authService.login(jwtAuthenticationRequest));
    }
}
