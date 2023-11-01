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

        UserDomain userDomain = getUserByUsername(request.getUsername());

        return JwtAuthenticationResponse.builder().token(jwtService.generateToken(userDomain)).build();
    }

    /**
     * Get UserDomain entity extended from UserDetails with username and active state
     * Method uses cache by username and enable in repository
     *
     * @return UserDetails
     * @throws UsernameNotFoundException If user not exists
     */
    public UserDomain getUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
