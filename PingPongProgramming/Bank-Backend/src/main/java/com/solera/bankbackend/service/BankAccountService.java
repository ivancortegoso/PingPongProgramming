package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.mapper.CreateBankAccountRequestToBankAccount;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import jakarta.persistence.Access;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class BankAccountService extends CommonService<BankAccount, IBankAccountRepository> {
    @Autowired
    UserService userService;
    CreateBankAccountRequestToBankAccount bankAccountMapper = Mappers.getMapper(CreateBankAccountRequestToBankAccount.class);

    public void depositMoney(double balance, Long id) {
        Optional<BankAccount> query = repository.findById(id);
        if (query.isPresent()) {
            query.get().setBalance(query.get().getBalance() + balance);
            repository.save(query.get());
        }
    }

    public void withdrawMoney(double balance, Long id) {
        Optional<BankAccount> query = repository.findById(id);
        if (query.isPresent() && query.get().getBalance() >= balance) {
            query.get().setBalance(query.get().getBalance() - balance);
            repository.save(query.get());
        }
    }

    public Set<BankAccount> findAllByUserAndEnabled() {
        User user = userService.getLogged();
        return repository.findAllByUserAndEnabled(user, true);
    }

    public Set<BankAccount> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public void delete(Long id) {
        User user = userService.getLogged();
        BankAccount bankAccount = this.findById(id);
        if (bankAccount != null) {
            if (bankAccount.getUser().equals(user)) {
                userService.depositMoney(bankAccount.getBalance());
                bankAccount.setEnabled(false);
                repository.save(bankAccount);
            } else {
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logged user is not allowed to delete this bank account.");
                //TODO
            }
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account does not exist.");
        }
    }

    public BankAccount findByIdAndEnabled(Long receiverId) {
        return repository.findByIdAndEnabled(receiverId, true);
    }

    public void create(CreateBankAccountRequest request) {
        User user = userService.getLogged();
        if (user.getBalance() >= request.getBalance()) {
            BankAccount newBankAccount = bankAccountMapper.toBankAccount(request);
            newBankAccount.setUser(user);
            userService.withdrawMoney(request.getBalance());
            this.save(newBankAccount);
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User balance is insufficient");
        }
    }

    public void deposit(DepositMoneyBankaccountRequest request) {
        User user = userService.getLogged();
        BankAccount bankAccount = this.findByIdAndEnabled(request.getReceiverId());
        if (bankAccount != null) {
            if (user.equals(bankAccount.getUser())) {
                if (user.getBalance() >= request.getBalance()) {
                    userService.withdrawMoney(request.getBalance());
                    this.depositMoney(request.getBalance(), request.getReceiverId());
                } else {
                    //TODO
                    //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User logged has not enough balance.");
                }
            } else {
                //TODO
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account with id " + request.getReceiverId() + " doesn't belong to user logged.");
            }
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account with id " + request.getReceiverId() + " doesn't exist.");
        }
    }
    public void withdraw(WithdrawMoneyBankaccountRequest request) {
        User user = userService.getLogged();
        BankAccount bankAccount = this.findByIdAndEnabled(request.getSenderId());
        if (bankAccount != null) {
            if (user.equals(bankAccount.getUser())) {
                if (bankAccount.getBalance() >= request.getBalance()) {
                    userService.depositMoney(request.getBalance());
                    this.withdrawMoney(request.getBalance(), request.getSenderId());
                } else {
                    //TODO
                    //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account has not enough balance.");
                }
            } else {
                //TODO
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account with id " + request.getSenderId() + " doesn't belong to user logged.");
            }
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account with id " + request.getSenderId() + " doesn't exist.");
        }
    }

}