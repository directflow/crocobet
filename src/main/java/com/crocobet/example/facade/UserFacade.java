package com.crocobet.example.facade;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exceptions.DuplicateUserException;
import com.crocobet.example.exceptions.UserNotFoundException;
import com.crocobet.example.service.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFacade {

    private final UserService userService;

    public UserFacade(UserService userService) {
        this.userService = userService;
    }

    /**
     * Read all UserDomain entities from db and converting to new UserDomain response object list
     *
     * @return List of UserDomain objects
     */
    public List<UserDomain> getUserList() {
        return userService.getUserList().stream().map(this::buildResponseDTO).toList();
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

    public UserDomain addUser(UserDomain userDomain) throws DuplicateUserException {
        return buildResponseDTO(userService.addUser(buildRequestDTO(userDomain)));
    }

    public UserDomain updateUser(Integer id, UserDomain userDomain) throws UserNotFoundException, DuplicateUserException {
        return buildResponseDTO(userService.updateUser(id, buildRequestDTO(userDomain)));
    }

    /**
     * Delete user by id
     *
     * @param id UserDomain id
     * @throws UserNotFoundException If user not exists
     */
    public void deleteUser(Integer id) throws UserNotFoundException {
        userService.deleteUser(id);
    }

    /**
     * Build new UserDomain object from entity with Lombok builder
     * Pass password for security
     *
     * @param userDomain UserDomain entity
     * @return Converted to UserDomain object
     */
    private UserDomain buildResponseDTO(UserDomain userDomain) {

        return UserDomain
                .builder()
                .id(userDomain.getId())
                .username(userDomain.getUsername())
                .email(userDomain.getEmail())
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .createDate(userDomain.getCreateDate())
                .modifyDate(userDomain.getModifyDate())
                .enabled(userDomain.getEnabled())
                .role(userDomain.getRole())
                .build();
    }

    /**
     * Build new UserDomain object to entity with Lombok builder
     * Pass id, createDate, modifyDate, role for security
     *
     * @param userDomain UserDomain object
     * @return Converted to UserDomain entity
     */
    private UserDomain buildRequestDTO(UserDomain userDomain) {

        return UserDomain
                .builder()
                .username(userDomain.getUsername())
                .password(userDomain.getPassword())
                .email(userDomain.getEmail())
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .build();
    }
}
