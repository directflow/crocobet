package com.crocobet.example.facade;

import com.crocobet.example.domain.role.Role;
import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.builder.UserDomainBuilder;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.crocobet.example.builder.UserDomainBuilder.buildRequestDTO;
import static com.crocobet.example.builder.UserDomainBuilder.buildResponseDTO;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    /**
     * Read all UserDomain entities from db and converting to new UserDomain response object list
     *
     * @return List of UserDomain objects
     */
    public List<UserDomain> getUserList() {
        return userService.getUserList().stream().map(UserDomainBuilder::buildResponseDTO).toList();
    }

    /**
     * Get UserDomain by id and converting to new UserDomain response object
     *
     * @param id UserDomain id
     * @return UserDomain object
     * @throws UserNotFoundException If user not exists
     */
    public UserDomain getUserById(Integer id) throws UserNotFoundException {
        return buildResponseDTO(userService.getUserById(id));
    }

    /**
     * Add new user to system
     * Build UserDomain object from request
     * Add USER role as default
     *
     * @param userDomain UserDomain object
     * @return Updated UserDomain entity
     * @throws UserDuplicateException If user was found by username or email and active state
     */
    public UserDomain addUser(UserDomain userDomain) throws UserDuplicateException {
        UserDomain buildUserDomain = buildRequestDTO(userDomain);
        buildUserDomain.setRole(Role.USER);
        return buildResponseDTO(userService.addUser(buildUserDomain));
    }

    /**
     * Update UserDomain to system
     * Build UserDomain object from request
     *
     * @param id         UserDomain id
     * @param userDomain UserDomain object
     * @return Updated UserDomain entity
     * @throws UserNotFoundException  If user not exists with id
     * @throws UserDuplicateException If user was found by username or email and active state
     */
    public UserDomain updateUser(Integer id, UserDomain userDomain) throws UserNotFoundException, UserDuplicateException {
        return buildResponseDTO(userService.updateUser(id, buildRequestDTO(userDomain)));
    }

    /**
     * Delete user by id
     * Drop user cache by username and active state
     *
     * @param id UserDomain id
     * @throws UserNotFoundException If user not exists
     */
    public void deleteUser(Integer id) throws UserNotFoundException {
        UserDomain userDomain = userService.deleteUser(id);
        userService.dropUsernameEnabledCache(userDomain.getUsername(), true);
    }
}
