package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import com.solera.bankbackend.repository.ITransactionRepository;
import com.solera.bankbackend.service.TransactionService;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    TransactionService transactionService;
    @GetMapping(path = {"{id}"})
    @ResponseBody
    public ResponseEntity<?> getTransactionById(@PathVariable(name = "id") Long transactionId) {
        if(transactionRepository.findById(transactionId).isPresent()) {
            Transaction transaction = transactionRepository.findById(transactionId).get();
            return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transaction));
        } else {
            return ResponseEntity.badRequest().body("Transaction not found");
        }
    }
    @GetMapping(path = "all")
    @ResponseBody
    public ResponseEntity<?> getTransactionAll() {
        return ResponseEntity.ok(transactionService.findAllTransactionResponse());
    }
    @GetMapping(path = "user")
    @ResponseBody
    public ResponseEntity<?> getTransactionUser() {
        User user = userService.getLogged();
        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount b: bankAccountRepository.findAllByUser(user)) {
            for (Transaction t: b.getTransactionList()) {
                transactions.add(t);
            }
        }
        return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transactions));
    }
    @GetMapping(path = "friends")
    @ResponseBody
    public ResponseEntity<?> getTransactionFriends() {
        User user = userService.getLogged();
        List<Transaction> transactions  = new ArrayList<>();
        List<User> friends = user.getFriends();
        for (User u:friends) {
            for (BankAccount b: bankAccountRepository.findAllByUser(u)) {
                for (Transaction t: b.getTransactionList()) {
                    transactions.add(t);
                }
            }
        }
        return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transactions));
    }
}
