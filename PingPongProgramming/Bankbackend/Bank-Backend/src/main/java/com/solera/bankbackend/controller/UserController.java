package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.DepositMoneyUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.request.UpdateUserRequest;
import com.solera.bankbackend.domain.dto.responses.ApiError;
import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.model.Privilege;
import com.solera.bankbackend.domain.model.Role;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.PrivilegeService;
import com.solera.bankbackend.service.RoleService;
import com.solera.bankbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/user")
@Tag(name = "User", description = "API operations related with user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PrivilegeService privilegeService;
    @PutMapping
    @ResponseBody
    @Operation(
            summary = "Updates user account information",
            description = "Updates logged user account information, this method is able to update first name, last name, phone number, address, password and email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
    public ResponseEntity<?> putUserAccountInformation(@RequestBody UpdateUserRequest request) {
        User user = userService.getLogged();
        userService.updateUser(user, request);
        return ResponseEntity.ok("User updated successfully");
    }

    @GetMapping
    @ResponseBody
    @Operation(
            summary = "Returns logged user's account information",
            description = "Returns logged user's account username, first name, last name, balance and a list of this user's friends",
            tags = { "friends" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserAccountInformation.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
    public ResponseEntity<?> getUserAccountInformation() {
        return ResponseEntity.ok(userService.getUserAccountInformation());
    }

    @PostMapping("/addfriend")
    @ResponseBody
    @Operation(
            summary = "Adds friend to logged user",
            description = "Logged user adds the given user as a friend",
            tags = { "friends" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
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
    @Operation(
            summary = "Deposit to user account",
            description = "Deposit the given amount of money to the logged user bank account",
            tags = { "balance" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
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
    @Operation(
            summary = "Withdraw from user account",
            description = "Withdraw the given amount of money from the logged user bank account",
            tags = { "balance" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
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
    @Operation(
            summary = "Upgrade user account",
            description = "Upgrade logged user account into premium user",
            tags = { "premium user" })
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Bad request", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "Forbidden access", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "User not found", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = { @Content(schema = @Schema(implementation = ApiError.class), mediaType = "application/json") })
    })
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
