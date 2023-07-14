package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BankAccountService extends CommonService<BankAccount, IBankAccountRepository>{

    public void depositMoney (double balance, Long id) {
        Optional<BankAccount> query = repository.findById(id);
        if(query.isPresent()) {
            query.get().setBalance(query.get().getBalance() + balance);
            repository.save(query.get());
        }
    }

    public void withdrawMoney (double balance, Long id) {
        Optional<BankAccount> query = repository.findById(id);
        if(query.isPresent() && query.get().getBalance() >= balance) {
            query.get().setBalance(query.get().getBalance() - balance);
            repository.save(query.get());
        }
    }

    public Set<BankAccount> findAllByUserAndEnabled(User user) {
        return repository.findAllByUserAndEnabled(user, true);
    }
    public Set<BankAccount> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public void delete(BankAccount bankAccount) {
        bankAccount.setEnabled(false);
        repository.save(bankAccount);
    }

    public BankAccount findByIdAndEnabled(Long receiverId) {
        return repository.findByIdAndEnabled(receiverId, true);
    }
}