package com.crocobet.example.builder;

import com.crocobet.example.domain.role.Role;
import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.model.pulsar.PulsarUserModel;

public class PulsarUserModelBuilder {

    /**
     * Build PulsarUserModel from UserDomain object
     *
     * @param userDomain UserDomain object
     * @return PulsarUserModel object
     */
    public static PulsarUserModel buildPulsarUserDomain(UserDomain userDomain) {
        return PulsarUserModel
                .builder()
                .username(userDomain.getUsername())
                .password(userDomain.getPassword())
                .firstName(userDomain.getFirstName())
                .lastName(userDomain.getLastName())
                .email(userDomain.getEmail())
                .role(Role.USER)
                .build();
    }
}
