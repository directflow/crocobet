package com.crocobet.example.facade;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.service.user.UserBuilderUtil;
import com.crocobet.example.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.crocobet.example.service.user.UserBuilderUtil.buildRequestDTO;
import static com.crocobet.example.service.user.UserBuilderUtil.buildResponseDTO;

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
        return userService.getUserList().stream().map(UserBuilderUtil::buildResponseDTO).toList();
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

    public UserDomain addUser(UserDomain userDomain) throws UserDuplicateException {
        return buildResponseDTO(userService.addUser(buildRequestDTO(userDomain)));
    }

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
