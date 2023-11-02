package com.crocobet.example.service.user;

import com.crocobet.example.domain.user.UserDomain;

public class UserBuilderUtil {

    /**
     * Build new UserDomain object from entity with Lombok builder
     * Pass password for security
     *
     * @param userDomain UserDomain entity
     * @return Converted to UserDomain object
     */
    public static UserDomain buildResponseDTO(UserDomain userDomain) {

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
    public static UserDomain buildRequestDTO(UserDomain userDomain) {

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
