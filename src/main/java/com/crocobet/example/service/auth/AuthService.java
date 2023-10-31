package com.crocobet.example.service.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {
    UserDetailsService userDetailsService();
}
