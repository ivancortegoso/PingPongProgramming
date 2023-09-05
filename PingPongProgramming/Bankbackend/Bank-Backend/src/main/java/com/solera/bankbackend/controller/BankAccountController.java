package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.RoleService;
import com.solera.bankbackend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "/api/bankaccount")
@Tag(name = "Bank account", description = "API operations related with bank accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<Set<BankAccountResponse>> getUserBankAccounts() {
        Set<BankAccountResponse> bankAccounts = bankAccountService.findAllByUserAndEnabled();
        return ResponseEntity.ok(bankAccounts);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) throws ApiErrorException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(id);
        if (bankAccount == null) {
            throw new EntityNotFoundException("Bank account not found.");
        } else if (!bankAccount.getUser().equals(user)) {
            throw new ApiErrorException("Logged user is not the bank account owner");
        }
        bankAccountService.delete(bankAccount, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Void> createBankAccount(@RequestBody CreateBankAccountRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        if (user.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Not enough balance.");
        } else if (!user.getRoles().contains(roleService.findByName("ROLE_PREMIUM_USER"))  && bankAccountService.findAllByUserAndEnabled().size() >= 3) {
            throw new ApiErrorException("Maximum number of bank accounts reached.");
        }
        bankAccountService.create(request, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<Void> depositMoneyBankaccount(@RequestBody DepositMoneyBankaccountRequest request)
            throws ApiErrorException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(request.getReceiverId());
        if (bankAccount == null) {
            throw new EntityNotFoundException("Bank account not found.");
        } else if (!user.equals(bankAccount.getUser())) {
            throw new ApiErrorException("Logged user is not the owner of the bank account");
        } else if (user.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Insufficient balance");
        }
        bankAccountService.deposit(bankAccount, user, request.getBalance());
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<Void> withdrawMoneyBankaccount(@RequestBody WithdrawMoneyBankaccountRequest request)
            throws ApiErrorException {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findByIdAndEnabled(request.getSenderId());
        if (bankAccount == null) {
            throw new EntityNotFoundException("Bank account not found.");
        } else if (!user.equals(bankAccount.getUser())) {
            throw new ApiErrorException("Logged user is not the owner of the bank account");
        } else if (bankAccount.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Insufficient balance");
        }
        bankAccountService.withdraw(bankAccount, user, request.getBalance());
        return ResponseEntity.ok().build();
    }

}
