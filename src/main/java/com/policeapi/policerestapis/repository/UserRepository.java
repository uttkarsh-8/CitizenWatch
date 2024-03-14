package com.policeapi.policerestapis.repository;

import com.policeapi.policerestapis.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserNameOrEmail(String username, String email);

    Optional<User> findByUserName(String username);
    Optional<User> findByEmail(String email);
}
