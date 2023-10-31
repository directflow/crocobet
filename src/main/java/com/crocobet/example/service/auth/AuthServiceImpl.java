package com.crocobet.example.service.auth;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.model.jwt.JwtAuthenticationRequest;
import com.crocobet.example.model.jwt.JwtAuthenticationResponse;
import com.crocobet.example.service.jwt.JwtService;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse login(JwtAuthenticationRequest request) throws UsernameNotFoundException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDomain userDomain = userService.getUserByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        return JwtAuthenticationResponse.builder().token(jwtService.generateToken(userDomain)).build();
    }

}
