package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.model.User;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class CreateUserRequestToUser {
    public abstract User toUser(CreateUserRequest request);
}
