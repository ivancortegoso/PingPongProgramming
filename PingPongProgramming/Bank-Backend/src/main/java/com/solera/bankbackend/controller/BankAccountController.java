package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.delete(id);
        return ResponseEntity.ok("Bank account deleted successfully.");
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        bankAccountService.create(request);
        return ResponseEntity.ok("Bank account created successfully.");
    }

    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoneyBankaccount(@RequestBody DepositMoneyBankaccountRequest request) {
        bankAccountService.deposit(request);
        return ResponseEntity.ok("Deposit made successfully");
    }

    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdrawMoneyBankaccount(@RequestBody WithdrawMoneyBankaccountRequest request) {
        bankAccountService.withdraw(request);
        return ResponseEntity.ok("Withdraw made.");
    }

}
