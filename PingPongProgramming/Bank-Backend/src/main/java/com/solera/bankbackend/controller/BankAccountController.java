package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DeleteBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.mapper.BankAccountToBankAccountResponse;
import com.solera.bankbackend.domain.mapper.CreateBankAccountRequestToBankAccount;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/bankaccount")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    IBankAccountRepository bankAccountRepository;
    @Autowired
    UserService userService;

    CreateBankAccountRequestToBankAccount bankAccountMapper = Mappers.getMapper(CreateBankAccountRequestToBankAccount.class);

    BankAccountToBankAccountResponse bankAccountResponseMapper = Mappers.getMapper(BankAccountToBankAccountResponse.class);
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccounts() {
        User user = userService.getLogged();
        Set<BankAccount> bankAccounts = bankAccountService.findAllByUser(user);
        return ResponseEntity.ok(bankAccountResponseMapper.toBankAccountResponse(bankAccounts));
    }
    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteBankAccount(@PathVariable Long bankAccountId) {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findById(bankAccountId);
        if(bankAccount != null) {
            if (bankAccount.getUser().equals(user)) {
                userService.depositMoney(bankAccount.getBalance(), user);
                bankAccountService.delete(bankAccount);
                return ResponseEntity.ok("Bank account deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logged user is not allowed to delete this bank account.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account does not exist.");
        }
    }
    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        User user = userService.getLogged();
        if(user.getBalance() >= request.getBalance()) {
            BankAccount newBankAccount = bankAccountMapper.toBankAccount(request);
            newBankAccount.setUser(user);
            userService.withdrawMoney(request.getBalance(), user);
            bankAccountService.save(newBankAccount);
            return ResponseEntity.ok(newBankAccount.getName());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User balance is insufficient");
        }
    }
    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoneyBankaccount(@RequestBody DepositMoneyBankaccountRequest request) {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findById(request.getReceiverId());
        if(bankAccount != null) {
            if (user.equals(bankAccount.getUser())) {
                if(user.getBalance() >= request.getBalance()) {
                    userService.withdrawMoney(request.getBalance(), user);
                    bankAccountService.depositMoney(request.getBalance(), request.getReceiverId());
                    return ResponseEntity.ok("Deposit made.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User logged has not enough balance.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account with id " + request.getReceiverId() + " doesn't belong to user logged.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account with id " + request.getReceiverId() + " doesn't exist.");
        }
    }
    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdrawMoneyBankaccount(@RequestBody WithdrawMoneyBankaccountRequest request) {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findById(request.getSenderId());
        if(bankAccount != null) {
            if (user.equals(bankAccount.getUser())) {
                if(bankAccount.getBalance() >= request.getBalance()) {
                    userService.depositMoney(request.getBalance(), user);
                    bankAccountService.withdrawMoney(request.getBalance(), request.getSenderId());
                    return ResponseEntity.ok("Withdraw made.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account has not enough balance.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account with id " + request.getSenderId() + " doesn't belong to user logged.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bank account with id " + request.getSenderId() + " doesn't exist.");
        }
    }

}
