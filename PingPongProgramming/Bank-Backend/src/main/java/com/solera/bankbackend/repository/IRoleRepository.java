package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}
