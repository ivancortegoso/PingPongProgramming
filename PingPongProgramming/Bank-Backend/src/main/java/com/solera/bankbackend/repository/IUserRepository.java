package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
