package com.crocobet.example.service.user;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exceptions.DuplicateUserException;
import com.crocobet.example.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDomain> getUserByUsername(String username);

    List<UserDomain> getUserList();

    UserDomain getUserById(Integer id) throws UserNotFoundException;

    UserDomain addUser(UserDomain userDomain) throws DuplicateUserException;

    UserDomain updateUser(Integer id, UserDomain userDomain) throws UserNotFoundException, DuplicateUserException;

    void deleteUser(Integer id) throws UserNotFoundException;
}
