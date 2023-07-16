package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/bankaccount")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccounts() {
        Set<BankAccountResponse> bankAccounts = bankAccountService.findAllByUserAndEnabled();
        return ResponseEntity.ok(bankAccounts);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long id) throws ApiErrorException, ChangeSetPersister.NotFoundException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(id);
        if(bankAccount == null) {
            throw new ChangeSetPersister.NotFoundException();
        } else if (!bankAccount.getUser().equals(user)){
            throw new ApiErrorException("Logged user is not the bank account owner");
        }
        bankAccountService.delete(bankAccount, user);
        return ResponseEntity.ok("Bank account deleted successfully.");
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        if (user.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Not enough balance.");
        }
        bankAccountService.create(request, user);
        return ResponseEntity.ok("Bank account created successfully.");
    }

    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoneyBankaccount(@RequestBody DepositMoneyBankaccountRequest request) throws ChangeSetPersister.NotFoundException, ApiErrorException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(request.getReceiverId());
        if(bankAccount == null) {
            throw new ChangeSetPersister.NotFoundException();
        } else if (!user.equals(bankAccount.getUser())) {
            throw new ApiErrorException("Logged user is not the owner of the bank account");
        } else if (user.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Insufficient balance");
        }
        bankAccountService.deposit(bankAccount, user, request.getBalance());
        return ResponseEntity.ok("Deposit made successfully");
    }

    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdrawMoneyBankaccount(@RequestBody WithdrawMoneyBankaccountRequest request) throws ChangeSetPersister.NotFoundException, ApiErrorException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(request.getSenderId());
        if(bankAccount == null) {
            throw new ChangeSetPersister.NotFoundException();
        } else if (!user.equals(bankAccount.getUser())) {
            throw new ApiErrorException("Logged user is not the owner of the bank account");
        } else if (bankAccount.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Insufficient balance");
        }
        bankAccountService.withdraw(bankAccount, user, request.getBalance());
        return ResponseEntity.ok("Withdraw made.");
    }

}
