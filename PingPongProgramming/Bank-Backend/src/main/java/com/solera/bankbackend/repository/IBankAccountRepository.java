package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {
    Set<BankAccount> findAllByUser(User user);

    Set<BankAccount> findAllByUserAndEnabled(User user, boolean enabled);

    BankAccount findByIdAndEnabled(Long receiverId, boolean enabled);
}
