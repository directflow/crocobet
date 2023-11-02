package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.facade.ProfileFacade;
import com.crocobet.example.logging.Loggable;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@Tag(name = "Profile controller")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileFacade profileFacade;

    @Loggable
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<UserDomain> updateProfile(@Valid @RequestBody UserDomain userDomain) throws UserNotFoundException, UserDuplicateException {
        return ResponseEntity.ok(profileFacade.updateProfile(userDomain));
    }
}
