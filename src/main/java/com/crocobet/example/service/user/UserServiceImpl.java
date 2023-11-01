package com.crocobet.example.service.user;

import com.crocobet.example.domain.role.Role;
import com.crocobet.example.domain.user.UserDomain;
import com.crocobet.example.exception.UserDuplicateException;
import com.crocobet.example.exception.UserNotFoundException;
import com.crocobet.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Find user by username and active state for auth
     *
     * @param username Username
     * @return Optional of UserDomain entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserDomain> getUserByUsername(String username) {
        return userRepository.findOneByUsernameAndEnabled(username, true);
    }

    /**
     * Get All users with active state
     *
     * @return List of UserDomain entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserDomain> getUserList() {
        return userRepository.findAllByEnabled(true);
    }

    /**
     * Get UserDomain entity by user id and active state
     *
     * @param id User id
     * @return UserDomain entity
     * @throws UserNotFoundException if user not exists
     */
    @Override
    @Transactional(readOnly = true)
    public UserDomain getUserById(Integer id) throws UserNotFoundException {
        return userRepository.findByIdAndEnabled(id, true).orElseThrow(UserNotFoundException::new);
    }

    /**
     * Add new user to system
     * Check user duplication by username and active state
     *
     * @param userDomain UserDomain object
     * @return Persisted UserDomain entity with id
     * @throws UserDuplicateException If user was found by username or email and active state
     */
    @Override
    @Transactional
    public UserDomain addUser(UserDomain userDomain) throws UserDuplicateException {

        if (checkUserExistenceByUsername(userDomain.getUsername())) {
            throw new UserDuplicateException(userDomain.getUsername());
        }

        if (checkUserExistenceByEmail(userDomain.getEmail())) {
            throw new UserDuplicateException(userDomain.getEmail());
        }

        // Encrypt password
        userDomain.setPassword(passwordEncoder.encode(userDomain.getPassword()));

        // Set default create date on persisting new user
        userDomain.setCreateDate(LocalDateTime.now());

        // Set default active state on persisting new user
        userDomain.setEnabled(true);

        // tmp
        userDomain.setRole(Role.USER);

        return userRepository.save(userDomain);
    }

    /**
     * Update UserDomain to system
     *
     * @param id         UserDomain id
     * @param userDomain UserDomain object
     * @return Updated UserDomain entity
     * @throws UserNotFoundException  If user not exists with id
     * @throws UserDuplicateException If user was found by username or email and active state
     */
    @Override
    @Transactional
    public UserDomain updateUser(Integer id, UserDomain userDomain) throws UserNotFoundException, UserDuplicateException {

        UserDomain find = userRepository.findByIdAndEnabled(id, true).orElseThrow(UserNotFoundException::new);

        // If request username not equals to current username, check duplication
        if (!find.getUsername().equals(userDomain.getUsername())) {
            if (checkUserExistenceByUsername(userDomain.getUsername())) {
                throw new UserDuplicateException(userDomain.getUsername());
            }
        }

        // If request email not equals to current email, check duplication
        if (!find.getEmail().equals(userDomain.getEmail())) {
            if (checkUserExistenceByEmail(userDomain.getEmail())) {
                throw new UserDuplicateException(userDomain.getEmail());
            }
        }

        find.setUsername(userDomain.getUsername());
        // Encrypt password
        find.setPassword(passwordEncoder.encode(userDomain.getPassword()));
        find.setFirstName(userDomain.getFirstName());
        find.setLastName(userDomain.getLastName());
        find.setEmail(userDomain.getEmail());

        // Set update date to user
        find.setModifyDate(LocalDateTime.now());

        return userRepository.save(find);
    }

    /**
     * Delete user by id
     * State change to inactive
     *
     * @param id UserDomain id
     * @throws UserNotFoundException If user not exists
     */
    @Override
    @Transactional
    public void deleteUser(Integer id) throws UserNotFoundException {

        UserDomain find = userRepository.findByIdAndEnabled(id, true).orElseThrow(UserNotFoundException::new);

        // Set inactive state to user
        find.setEnabled(true);

        // Set delete date to user
        find.setModifyDate(LocalDateTime.now());

        userRepository.save(find);
    }

    /**
     * Check if user exists by username and active state
     *
     * @param username Username
     * @return Boolean result of query
     */
    @Transactional(readOnly = true)
    public boolean checkUserExistenceByUsername(String username) {
        return userRepository.existsByUsernameAndEnabled(username, true);
    }

    /**
     * Check if user exists by email and active state
     *
     * @param email Email
     * @return Boolean result of query
     */
    @Transactional(readOnly = true)
    public boolean checkUserExistenceByEmail(String email) {
        return userRepository.existsByEmailAndEnabled(email, true);
    }
}
