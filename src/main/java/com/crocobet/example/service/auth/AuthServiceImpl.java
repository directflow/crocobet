package com.crocobet.example.service.auth;

import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    /**
     * Get UserDomain entity extended from UserDetails with username
     *
     * @return UserDetailsService
     * @throws UsernameNotFoundException If user not exists
     */
    @Override
    public UserDetailsService getUserDetailsService() {
        return username -> userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
