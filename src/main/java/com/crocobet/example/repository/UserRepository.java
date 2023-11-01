package com.crocobet.example.repository;

import com.crocobet.example.domain.user.UserDomain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDomain, Integer> {
    boolean existsByUsernameAndEnabled(String username, Boolean enabled);

    boolean existsByEmailAndEnabled(String email, Boolean enabled);

    @Cacheable(value = "USERNAME_KEY_CACHE", key = "{#username,#enabled}")
    Optional<UserDomain> findOneByUsernameAndEnabled(String username, Boolean enabled);

    Optional<UserDomain> findByIdAndEnabled(Integer id, Boolean enabled);

    List<UserDomain> findAllByEnabled(Boolean enabled);
}
