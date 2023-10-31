package com.crocobet.example.service.auth;

import com.crocobet.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Get UserDomain entity extended from UserDetails with username and active state
     *
     * @return UserDetails
     * @throws UsernameNotFoundException If user not exists
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOneByUsernameAndEnabled(username, true)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
