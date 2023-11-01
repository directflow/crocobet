package com.crocobet.example;

import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.service.user.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(classes = Application.class)
public class UserCacheTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void userCacheTest() {
        UserDomain userDomain = buildUserDomain();

        assertDoesNotThrow(() -> {
            UserDomain persistedUserDomain = userService.addUser(userDomain);
            assertNotNull(persistedUserDomain);
            assertNotNull(persistedUserDomain.getId());

            assertTrue(userExists(persistedUserDomain.getUsername()));

            // Delete user without cache drop, because cache drop is calling from UserFacade.deleteUser
            userService.deleteUser(persistedUserDomain.getId());

            // User exists in cache, because UserRepository reads from cache
            assertTrue(userExists(persistedUserDomain.getUsername()));

            // Delete from db will throw UserNotFoundException, because deleteUser method check active user by id and state
            assertThrows(UserNotFoundException.class, () -> userService.deleteUser(persistedUserDomain.getId()));
        });
    }

    /**
     * Read from db and put to cache
     *
     * @param username Username
     * @return Existence result
     */
    private boolean userExists(String username) {
        return userService.getUserByUsername(username).isPresent();
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
