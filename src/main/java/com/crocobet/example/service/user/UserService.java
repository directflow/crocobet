package com.crocobet.example.service.user;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserDomain> getUserByUsername(String username);

    List<UserDomain> getUserList();

    UserDomain getUserById(Integer id) throws UserNotFoundException;

    UserDomain addUser(UserDomain userDomain) throws UserDuplicateException;

    UserDomain updateUser(Integer id, UserDomain userDomain) throws UserNotFoundException, UserDuplicateException;

    void deleteUser(Integer id) throws UserNotFoundException;
}
