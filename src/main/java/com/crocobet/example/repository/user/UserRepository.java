package com.crocobet.example.repository.user;

import com.crocobet.example.domain.user.UserDomain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDomain, Integer> {
    boolean existsByUsernameAndState(String username, Integer state);
    Optional<UserDomain> findOneByUsernameAndState(String username, Integer state);
    Optional<UserDomain> findByIdAndState(Integer id, Integer state);
    List<UserDomain> findAll();
    Optional<UserDomain> findById(Integer id);
}
