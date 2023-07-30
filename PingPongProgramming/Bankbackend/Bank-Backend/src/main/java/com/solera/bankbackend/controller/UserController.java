package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.DepositMoneyUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.request.UpdateUserRequest;
import com.solera.bankbackend.domain.model.Privilege;
import com.solera.bankbackend.domain.model.Role;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.PrivilegeService;
import com.solera.bankbackend.service.RoleService;
import com.solera.bankbackend.service.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;
    @PutMapping
    @ResponseBody
    public ResponseEntity<?> putUserAccountInformation(@RequestBody UpdateUserRequest request) {
        User user = userService.getLogged();
        userService.updateUser(user, request);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping
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

    @PutMapping(path = "/upgrade")
    @ResponseBody
    public ResponseEntity<?> upgradePremiumUser() throws ApiErrorException {
        User user = userService.getLogged();
        Privilege premiumUserPrivilege
                = privilegeService.createPrivilegeIfNotFound("PREMIUM_USER_PRIVILEGE");
        Role premiumUserRole = roleService.createRoleIfNotFound("ROLE_PREMIUM_USER", Arrays.asList(premiumUserPrivilege));
        if (user.getRoles().contains(premiumUserRole)) {
            throw new ApiErrorException("This user is already a premium user");
        }
        else if (user.getBalance() >= 50) {
            userService.upgradePremiumUser(user, 50, premiumUserRole);
            return ResponseEntity.ok("User upgraded successfully");
        } else {
            throw new ApiErrorException("Insufficient balance.");
        }
    }
}
