package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.*;
import com.solera.bankbackend.domain.dto.responses.FriendResponse;
import com.solera.bankbackend.domain.mapper.CreateBankAccountRequestToBankAccount;
import com.solera.bankbackend.domain.mapper.TransactionRequestToTransaction;
import com.solera.bankbackend.domain.mapper.UserAccountInformationToUser;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import com.solera.bankbackend.repository.ITransactionRepository;
import com.solera.bankbackend.repository.IUserRepository;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;
    @Autowired
    IBankAccountRepository bankAccountRepository;
    @Autowired
    ITransactionRepository transactionRepository;
    @Autowired
    IUserRepository userRepository;
    UserAccountInformationToUser userAccountInformationToUserMapper = Mappers.getMapper(UserAccountInformationToUser.class);
    TransactionRequestToTransaction transactionMapper = Mappers.getMapper(TransactionRequestToTransaction.class);
    CreateBankAccountRequestToBankAccount bankAccountMapper = Mappers.getMapper(CreateBankAccountRequestToBankAccount.class);
    @GetMapping(path = {""})
    @ResponseBody
    public ResponseEntity<?> getUserAccountInformation() {
        User user = userService.getLogged();
        return ResponseEntity.ok(userAccountInformationToUserMapper.toUserAccountInformation(user));
    }
    @GetMapping("/bankaccounts")
    @ResponseBody
    public ResponseEntity<?> getUserBankAccounts() {
        User user = userService.getLogged();
        Set<BankAccount> bankAccounts = bankAccountRepository.findAllByUser(user);
        return ResponseEntity.ok(bankAccounts);
    }
    @DeleteMapping(path = "/delete/bankaccount")
    @ResponseBody
    public ResponseEntity<?> deleteBankAccount(@RequestBody DeleteBankAccountRequest request) {
        User user = userService.getLogged();
        BankAccount bankAccount = bankAccountService.findById(request.getId());
        if(bankAccount != null) {
            if (bankAccount.getUser().equals(user)) {
                userService.depositMoney(bankAccount.getBalance(), user);
                bankAccountRepository.delete(bankAccount);
                return ResponseEntity.ok("Bank account deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Logged user is not allowed to delete this bank account.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bank account does not exist.");
        }
    }
    @PostMapping(path = "/create/bankaccount")
    @ResponseBody
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        User user = userService.getLogged();
        if(user.getBalance() >= request.getBalance()) {
            BankAccount newBankAccount = bankAccountMapper.toBankAccount(request);
            newBankAccount.setUser(user);
            userService.withdrawMoney(request.getBalance(), user);
            bankAccountRepository.save(newBankAccount);
            return ResponseEntity.ok(newBankAccount.getName());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User balance is insufficient");
        }
    }
    @PostMapping("/addfriend")
    @ResponseBody
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest request) {
        User user = userService.getLogged();
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            User friend = userRepository.findByUsername(request.getUsername()).get();
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            userRepository.save(user);
            userRepository.save(friend);
            return ResponseEntity.ok(new FriendResponse(request.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + request.getUsername() + " does not exist");
        }
    }
    @PostMapping(path = "/create/transaction")
    @ResponseBody
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        User user = userService.getLogged();
        BankAccount sender = bankAccountRepository.findById(request.getSenderID()).isPresent() ? bankAccountRepository.findById(request.getSenderID()).get() : null;
        BankAccount receiver = bankAccountRepository.findById(request.getReceiverID()).isPresent() ? bankAccountRepository.findById(request.getReceiverID()).get() : null;
        if(sender == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender bank account not found");
        } else if(receiver == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Receiver bank account not found");
        } else if(!sender.getUser().equals(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User logged is not the owner of the sender bank account");
        } else {
            Transaction transaction = transactionMapper.toTransaction(request);
            if (sender.getBalance() >= request.getBalance()) {
                transactionRepository.save(transaction);
                sender.getTransactionList().add(transaction);
                sender.setBalance(sender.getBalance() - request.getBalance());
                receiver.setBalance(receiver.getBalance() + request.getBalance());
                bankAccountRepository.save(sender);
                bankAccountRepository.save(receiver);

                return ResponseEntity.ok(sender.getBalance());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough balance");
            }
        }
    }
    @PostMapping(path = "/bankaccount/deposit")
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
    @PostMapping(path = "/bankaccount/withdraw")
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

    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoney(@RequestBody DepositMoneyUserRequest request) {
        User user = userService.getLogged();
        userService.depositMoney(request.getBalance(), user);
        return ResponseEntity.ok("Deposit done.");
    }
    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdraw(@RequestBody DepositMoneyUserRequest request) {
        User user = userService.getLogged();
        userService.withdrawMoney(request.getBalance(), user);
        return ResponseEntity.ok("withdraw done.");
    }
}
