package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/admin/")
@Hidden
public class AdminController {
    @Autowired
    UserService userService;

    @DeleteMapping("deleteuser/{username}")
    @ResponseBody
    public ResponseEntity<?> deleteUserAccount(@PathVariable String username) {
        User userDelete = userService.findByUsernameAndEnabled(username)
                .orElseThrow(() -> new EntityNotFoundException("Bank account not found."));
        userService.deleteUser(userDelete);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
