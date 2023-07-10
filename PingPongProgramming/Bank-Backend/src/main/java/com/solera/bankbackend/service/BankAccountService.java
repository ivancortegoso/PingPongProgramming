package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.repository.IBankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService extends CommonService<BankAccount, IBankAccountRepository>{
    @Autowired
    IBankAccountRepository bankAccountRepository;
    public void depositMoney (double balance, Long id) {
        Optional<BankAccount> query = bankAccountRepository.findById(id);
        if(query.isPresent()) {
            query.get().setBalance(query.get().getBalance() + balance);
            bankAccountRepository.save(query.get());
        }
    }

    public void withdrawMoney (double balance, Long id) {
        Optional<BankAccount> query = bankAccountRepository.findById(id);
        if(query.isPresent() && query.get().getBalance() >= balance) {
            query.get().setBalance(query.get().getBalance() - balance);
            bankAccountRepository.save(query.get());
        }
    }
}
