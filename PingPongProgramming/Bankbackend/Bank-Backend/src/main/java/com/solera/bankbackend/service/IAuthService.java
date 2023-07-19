package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.LoginDto;

public interface IAuthService {
    String login(LoginDto loginDto);
}