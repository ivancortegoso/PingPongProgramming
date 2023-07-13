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
     @GetMapping(path = {""})
    @ResponseBody
    public ResponseEntity<?> getUserAccountInformation() {
        User user = userService.getLogged();
        return ResponseEntity.ok(userAccountInformationToUserMapper.toUserAccountInformation(user));
    }
    @PostMapping("/addfriend")
    @ResponseBody
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest request) {
        User user = userService.getLogged();
        if(userRepository.findByUsername(request.getUsername()).isPresent()) {
            User friend = userRepository.findByUsername(request.getUsername()).get();
            if (friend.getFriends().contains(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users are already friends");
            }
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            userRepository.save(user);
            userRepository.save(friend);
            return ResponseEntity.ok(new FriendResponse(request.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + request.getUsername() + " does not exist");
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
