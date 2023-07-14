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
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;
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
        if(userService.findByUsername(request.getUsername()).isPresent()) {
            User friend = userService.findByUsername(request.getUsername()).get();
            if (friend.getFriends().contains(user)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users are already friends");
            }
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            userService.save(user);
            userService.save(friend);
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
