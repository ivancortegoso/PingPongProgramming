package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(String name);
}
