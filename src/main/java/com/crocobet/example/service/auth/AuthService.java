package com.crocobet.example.service.auth;

import com.crocobet.example.model.jwt.JwtAuthenticationRequest;
import com.crocobet.example.model.jwt.JwtAuthenticationResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {
    JwtAuthenticationResponse login(JwtAuthenticationRequest request) throws UsernameNotFoundException;
}
