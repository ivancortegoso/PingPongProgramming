package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public abstract class CreateUserRequestToUser {
    public abstract User toUser(CreateUserRequest request);
    public abstract CreateUserRequest toCreateUserRequest(User user);
}
