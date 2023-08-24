package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.LoginDto;
import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.responses.JWTAuthResponse;
import com.solera.bankbackend.domain.mapper.UserMapper;
import com.solera.bankbackend.service.AuthServiceImpl;
import com.solera.bankbackend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/public/")
@Tag(name = "Authentication", description = "API authentication functions, register and login")
public class AuthenticationController {
    @Autowired
    protected UserService userService;
    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    UserMapper mapper;

    // Build Login REST API
    @PostMapping("login")
    public ResponseEntity<JWTAuthResponse> authenticate(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
    @PostMapping(path = "register")
    public ResponseEntity<?> register(@RequestBody CreateUserRequest request) throws ApiErrorException {
        if (userService.findByEmail(request.getEmail()) != null) {
            throw new ApiErrorException("Email already exists!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }
}
