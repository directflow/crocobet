package com.crocobet.example;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void getUserByUsernameExistsTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            Optional<UserDomain> getUserDomain = userService.getUserByUsername(persistedUserDomain.getUsername());
            assertTrue(getUserDomain.isPresent());
        });
    }

    @Test
    public void getUserByUsernameNotExistsTest() {
        Optional<UserDomain> userDomain = userService.getUserByUsername(getRandomString());
        assertTrue(userDomain.isEmpty());
    }

    @Test
    public void getUserListTest() {
        List<UserDomain> userDomainList = userService.getUserList();
        assertFalse(userDomainList.isEmpty());
    }

    @Test
    public void getUserByIdExistsTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            assertDoesNotThrow(() -> userService.getUserById(persistedUserDomain.getId()));
        });
    }

    @Test
    public void getUserByIdNotExistsTest() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(0));
    }

    @Test
    public void addUserTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            UserDomain getUserDomain = userService.getUserById(persistedUserDomain.getId());
            assertNotNull(getUserDomain);
        });
    }

    @Test
    public void updateUserTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            String newFirstName = getRandomString();

            persistedUserDomain.setFirstName(newFirstName);
            UserDomain updatedUserDomain = userService.updateUser(persistedUserDomain.getId(), persistedUserDomain);
            assertNotNull(updatedUserDomain);
            assertEquals(updatedUserDomain.getFirstName(), newFirstName);

            UserDomain getUserDomain = userService.getUserById(persistedUserDomain.getId());
            assertNotNull(getUserDomain);
            assertEquals(getUserDomain.getFirstName(), newFirstName);
        });
    }

    @Test
    public void deleteUserTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            UserDomain getUserDomain = userService.getUserById(persistedUserDomain.getId());
            assertNotNull(getUserDomain);

            userService.deleteUser(persistedUserDomain.getId());
        });
    }

    @Test
    public void notExistDeleteUserTest() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(0));
    }

    private UserDomain buildUserDomain() {
        return UserDomain
                .builder()
                .username(getRandomString())
                .password(getRandomString())
                .firstName(getRandomString())
                .lastName(getRandomString())
                .email(getRandomString() + "@gmail.com")
                .build();
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphabetic(10).toLowerCase();
    }
}
