package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exceptions.DuplicateUserException;
import com.crocobet.example.exceptions.UserNotFoundException;
import com.crocobet.example.facade.UserFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("")
    public List<UserDomain> getUserList() {
        return userFacade.getUserList();
    }

    @GetMapping("/{id}")
    public UserDomain getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return userFacade.getUserById(id);
    }

    @PostMapping("")
    public UserDomain addUser(@Valid @RequestBody UserDomain userDomain) throws DuplicateUserException {
        return userFacade.addUser(userDomain);
    }

    @PutMapping("/{id}")
    public UserDomain updateUser(@PathVariable Integer id, @Valid @RequestBody UserDomain userDomain) throws UserNotFoundException, DuplicateUserException {
        return userFacade.updateUser(id, userDomain);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        userFacade.deleteUser(id);
    }
}