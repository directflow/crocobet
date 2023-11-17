package com.crocobet.example.controller;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.facade.PulsarFacade;
import com.crocobet.example.logging.Loggable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pulsar")
@Tag(name = "Pulsar controller")
@RequiredArgsConstructor
public class PulsarController {

    private final PulsarFacade pulsarFacade;

    @Loggable
    @PutMapping("/addUser")
    @Operation(summary = "Add new user with USER role and send to Pulsar")
    public ResponseEntity<MessageId> addUser(@Valid @RequestBody UserDomain userDomain) throws PulsarClientException {
        return ResponseEntity.ok(pulsarFacade.addUser(userDomain));
    }
}
