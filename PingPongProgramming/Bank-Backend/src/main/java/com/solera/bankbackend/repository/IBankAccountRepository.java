package com.solera.bankbackend.repository;

import com.solera.bankbackend.domain.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {
}
