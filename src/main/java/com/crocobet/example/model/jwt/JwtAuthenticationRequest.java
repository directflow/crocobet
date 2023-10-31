package com.crocobet.example.model.jwt;

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
public class JwtAuthenticationRequest {

    @Size(min = 4, max = 100)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String username;

    @Size(min = 6, max = 50)
    private String password;
}
