package com.crocobet.example.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", indexes = {
        @Index(name = "multiIndexIdState", columnList = "id, state"),
        @Index(name = "multiIndexEmailState", columnList = "email, state"),
        @Index(name = "multiIndexUsernameState", columnList = "username, state")
})
public class UserDomain implements Serializable {

    public static final Integer INACTIVE_STATE = 0;
    public static final Integer ACTIVE_STATE = 1;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @Column(name = "username", nullable = false)
    private String username;

    @Size(min = 6, max = 50)
    @Column(name = "password", nullable = false)
    private String password;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
    @Column(name = "email", nullable = false)
    private String email;

    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Column(name = "state", nullable = false)
    private Integer state;

}
