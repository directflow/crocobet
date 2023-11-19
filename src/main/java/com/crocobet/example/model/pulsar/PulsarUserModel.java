package com.crocobet.example.model.pulsar;

import com.crocobet.example.domain.role.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PulsarUserModel {

    @Size(min = 4, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String username;

    @Size(min = 4, max = 100)
    private String password;

    @Size(min = 4, max = 100)
    @Email(regexp = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}")
    private String email;

    @Size(min = 2, max = 100)
    private String firstName;

    @Size(min = 2, max = 100)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Double balance;

}
