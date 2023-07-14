package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.DepositMoneyUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.responses.FriendResponse;
import com.solera.bankbackend.domain.mapper.UserAccountInformationToUser;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping(path = {""})
    @ResponseBody
    public ResponseEntity<?> getUserAccountInformation() {
        return ResponseEntity.ok(userService.getUserAccountInformation());
    }
    @PostMapping("/addfriend")
    @ResponseBody
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest request) {
        userService.addFriend(request);
        return ResponseEntity.ok("Friend added successfully.");
    }
    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoney(@RequestBody DepositMoneyUserRequest request) {
        userService.depositMoney(request.getBalance());
        return ResponseEntity.ok("Deposit done.");
    }
    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdraw(@RequestBody DepositMoneyUserRequest request) {
        userService.withdrawMoney(request.getBalance());
        return ResponseEntity.ok("withdraw done.");
    }
}
