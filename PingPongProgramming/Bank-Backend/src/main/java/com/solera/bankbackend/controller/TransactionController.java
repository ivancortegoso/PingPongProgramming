package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import com.solera.bankbackend.repository.ITransactionRepository;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "/api/transaction/")
public class TransactionController {
    @Autowired
    IBankAccountRepository bankAccountRepository;
    @Autowired
    UserService userService;
    @Autowired
    ITransactionRepository transactionRepository;
    @GetMapping(path = {"id"})
    @ResponseBody
    public ResponseEntity<?> getTransactionById(@PathVariable(name = "id") Long transactionId) {
        if(transactionRepository.findById(transactionId).isPresent()) {
            Transaction transaction = transactionRepository.findById(transactionId).get();
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.badRequest().body("Transaction not found");
        }
    }
    @GetMapping(path = "all")
    @ResponseBody
    public ResponseEntity<?> getTransactionAll() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }
    @GetMapping(path = "user")
    @ResponseBody
    public ResponseEntity<?> getTransactionUser() {
        User user = userService.getLogged();
        Set<Transaction> transactions = new HashSet<>();
        for (BankAccount b: bankAccountRepository.findAllByUser(user)) {
            for (Transaction t: b.getTransactionList()) {
                transactions.add(t);
            }
        }
        return ResponseEntity.ok(transactions);
    }
    @GetMapping(path = "friends")
    @ResponseBody
    public ResponseEntity<?> getTransactionFriends() {
        User user = userService.getLogged();
        Set<Transaction> transactions  = new HashSet<>();
        Set<User> friends = user.getFriends();
        for (User u:friends) {
            for (BankAccount b: bankAccountRepository.findAllByUser(u)) {
                for (Transaction t: b.getTransactionList()) {
                    transactions.add(t);
                }
            }
        }
        return ResponseEntity.ok(transactions);
    }
}
