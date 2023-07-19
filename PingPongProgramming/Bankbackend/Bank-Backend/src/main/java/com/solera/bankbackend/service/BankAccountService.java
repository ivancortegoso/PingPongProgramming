package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.DepositMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.request.WithdrawMoneyBankaccountRequest;
import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.domain.mapper.BankAccountMapper;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BankAccountService extends CommonService<BankAccount, IBankAccountRepository> {
    @Autowired
    UserService userService;
    @Autowired
    BankAccountMapper bankAccountMapper;

    public void depositMoney(double balance, BankAccount bankAccount) {
        bankAccount.depositMoney(balance);
        repository.save(bankAccount);
    }

    public void withdrawMoney(double balance, BankAccount bankAccount) {
        bankAccount.withdrawMoney(balance);
        repository.save(bankAccount);
    }

    public Set<BankAccountResponse> findAllByUserAndEnabled() {
        User user = userService.getLogged();
        Set<BankAccountResponse> bankAccountsByUser = bankAccountMapper.toBankAccountResponse(repository.findAllByUserAndEnabled(user, true));
        return bankAccountsByUser;
    }

    public Set<BankAccount> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public void delete(BankAccount bankAccount, User user) {
        userService.depositMoney(user, bankAccount.getBalance());
        bankAccount.setEnabled(false);
        repository.save(bankAccount);
    }

    public BankAccount findByIdAndEnabled(Long receiverId) {
        return repository.findByIdAndEnabled(receiverId, true);
    }

    public void create(CreateBankAccountRequest request, User user) {
        BankAccount newBankAccount = bankAccountMapper.toBankAccount(request);
        newBankAccount.setUser(user);
        userService.withdrawMoney(request.getBalance(), user);
        this.save(newBankAccount);
    }

    public void deposit(BankAccount bankAccount, User user, double balance) {
        userService.withdrawMoney(balance, user);
        this.depositMoney(balance, bankAccount);
    }

    public void withdraw(BankAccount bankAccount, User user, double balance) {
        userService.depositMoney(user, balance);
        this.withdrawMoney(balance, bankAccount);
    }

}