package com.crocobet.example.facade;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.crocobet.example.service.user.UserBuilderUtil.buildRequestDTO;
import static com.crocobet.example.service.user.UserBuilderUtil.buildResponseDTO;

@Component
@RequiredArgsConstructor
public class ProfileFacade {

    private final UserService userService;

    /**
     * Get UserDomain from SecurityContextHolder and update own profile with UserService
     *
     * @param userDomain UserDomain object
     * @return Updated UserDomain
     * @throws UserNotFoundException  If user not exists
     * @throws UserDuplicateException If username or email are duplicated
     */
    public UserDomain updateProfile(UserDomain userDomain) throws UserNotFoundException, UserDuplicateException {
        UserDomain userDetails = getUserDetails();
        return buildResponseDTO(userService.updateUser(userDetails.getId(), buildRequestDTO(userDomain)));
    }

    /**
     * Get user details for authorized user from SecurityContextHolder
     *
     * @return UserDomain object
     */
    private UserDomain getUserDetails() {
        return (UserDomain) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
