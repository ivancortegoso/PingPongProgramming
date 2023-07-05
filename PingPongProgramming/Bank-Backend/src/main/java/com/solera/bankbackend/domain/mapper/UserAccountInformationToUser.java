package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.model.User;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserAccountInformationToUser {
    public abstract User toUser(UserAccountInformation request);
    public abstract UserAccountInformation toUserAccountInformation(User user);
}
