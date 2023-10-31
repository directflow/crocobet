package com.crocobet.example.service.auth;

import com.crocobet.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    /**
     * Get UserDomain entity extended from UserDetails with username
     *
     * @return UserDetailsService
     * @throws UsernameNotFoundException If user not exists
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findOneByUsernameAndEnabled(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
