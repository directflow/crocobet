package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.facade.AdminFacade;
import com.crocobet.example.logging.Loggable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Admin controller")
@RequiredArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    @Loggable
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get user by id, authorized with ADMIN role")
    public ResponseEntity<UserDomain> getUserById(@PathVariable Integer id) throws UserNotFoundException {
        return ResponseEntity.ok(adminFacade.getUserById(id));
    }

    @Loggable
    @PutMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add new user with USER or ADMIN role, authorized with ADMIN role")
    public ResponseEntity<UserDomain> addAdminUser(@Valid @RequestBody UserDomain userDomain) throws UserDuplicateException {
        return ResponseEntity.ok(adminFacade.addAdminUser(userDomain));
    }
}
