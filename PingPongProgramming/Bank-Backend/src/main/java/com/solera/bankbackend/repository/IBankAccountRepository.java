package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findBankAccountByUser(User user);
}
