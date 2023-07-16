package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.DepositMoneyUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<?> addFriend(@RequestBody FriendRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        Optional<User> friend = userService.findByUsernameAndEnabled(request.getUsername());
        if (!friend.isPresent()) {
            throw new ApiErrorException("User with username: " + request.getUsername() + " does not exist.");
        } else if (user.getFriends().contains(friend.get())) {
            throw new ApiErrorException("Users are already friends");
        }
        userService.addFriend(user, friend.get());
        return ResponseEntity.ok("Friend added successfully.");
    }

    @PostMapping(path = "/deposit")
    @ResponseBody
    public ResponseEntity<?> depositMoney(@RequestBody DepositMoneyUserRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        if (request.getBalance() < 0) {
            throw new ApiErrorException("Negative balance deposit");
        }
        userService.depositMoney(user, request.getBalance());
        return ResponseEntity.ok("Deposit done.");
    }

    @PostMapping(path = "/withdraw")
    @ResponseBody
    public ResponseEntity<?> withdraw(@RequestBody DepositMoneyUserRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        if (user.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Insufficient balance.");
        } else if (request.getBalance() < 0) {
            throw new ApiErrorException("Negative balance withdraw.");
        }
        userService.withdrawMoney(request.getBalance(), user);
        return ResponseEntity.ok("withdraw done.");
    }
}
