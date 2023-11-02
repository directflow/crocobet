package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.facade.UserFacade;
import com.crocobet.example.logging.Loggable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDomain> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(userFacade.getUserById(id));
    }

    /*
     I think Post method would be better for updateUser
     */
    @Loggable
    @PostMapping("")
    public ResponseEntity<UserDomain> addUser(@Valid @RequestBody UserDomain userDomain) throws UserDuplicateException {
        return ResponseEntity.ok(userFacade.addUser(userDomain));
    }

    /*
     I think Put method would be better for addUser
     */
    @Loggable
    @PutMapping("/{id}")
    public ResponseEntity<UserDomain> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDomain userDomain) throws UserNotFoundException, UserDuplicateException {
        return ResponseEntity.ok(userFacade.updateUser(id, userDomain));
    }

    @Loggable
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) throws UserNotFoundException {
        userFacade.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}