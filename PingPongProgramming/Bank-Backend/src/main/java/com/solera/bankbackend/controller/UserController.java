package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
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
import com.solera.bankbackend.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {
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
        System.out.println("HERE WE ARE");
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
    @PostMapping(path = "/create/bankaccount")
    @ResponseBody
    public ResponseEntity<?> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        User user = userService.getLogged();
        BankAccount newBankAccount = bankAccountMapper.toBankAccount(request);
        newBankAccount.setUser(user);
        bankAccountRepository.save(newBankAccount);
        return ResponseEntity.ok(newBankAccount.getName());
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
            return ResponseEntity.ok(new FriendResponse(request.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with username " + request.getUsername() + " does not exist");
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
        } else {
            Transaction transaction = transactionMapper.toTransaction(request);
            if (sender.getBalance() >= request.getBalance()) {
                transactionRepository.save(transaction);
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
}
