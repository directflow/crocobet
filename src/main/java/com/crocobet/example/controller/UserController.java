package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exceptions.DuplicateUserException;
import com.crocobet.example.exceptions.UserNotFoundException;
import com.crocobet.example.facade.UserFacade;
import com.crocobet.example.logging.Loggable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User controller")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("")
    public List<UserDomain> getUserList() {
        return userFacade.getUserList();
    }

    @Loggable
    @GetMapping("/{id}")
    public UserDomain getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return userFacade.getUserById(id);
    }

    /*
     I think Post method would be better for updateUser
     */
    @Loggable
    @PostMapping("")
    public UserDomain addUser(@Valid @RequestBody UserDomain userDomain) throws DuplicateUserException {
        return userFacade.addUser(userDomain);
    }

    /*
     I think Put method would be better for addUser
     */
    @Loggable
    @PutMapping("/{id}")
    public UserDomain updateUser(@PathVariable Integer id, @Valid @RequestBody UserDomain userDomain) throws UserNotFoundException, DuplicateUserException {
        return userFacade.updateUser(id, userDomain);
    }

    @Loggable
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        userFacade.deleteUser(id);
    }
}