package com.crocobet.example.facade;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.crocobet.example.service.user.UserBuilderUtil.buildRequestDTO;
import static com.crocobet.example.service.user.UserBuilderUtil.buildResponseDTO;

@Component
@RequiredArgsConstructor
public class AdminFacade {

    private final UserService userService;

    /**
     * Add new user with USER or ADMIN role to system
     * Build UserDomain object from request
     * Add ADMIN role as default
     *
     * @param userDomain UserDomain object
     * @return Updated UserDomain entity
     * @throws UserDuplicateException If user was found by username or email and active state
     */
    public UserDomain addAdminUser(UserDomain userDomain) throws UserDuplicateException {
        UserDomain buildUserDomain = buildRequestDTO(userDomain);
        buildUserDomain.setRole(userDomain.getRole());
        return buildResponseDTO(userService.addUser(buildUserDomain));
    }
}
