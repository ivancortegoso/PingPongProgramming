package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.request.UpdateUserRequest;
import com.solera.bankbackend.domain.dto.responses.FriendResponse;
import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User toUser(CreateUserRequest request);
    public abstract UserAccountInformation toUserAccountInformation(User user);
    @Mapping(target = "name", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    public abstract FriendResponse ToFriendResponse(User user);
    @Mapping(target = "name", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    public abstract List<FriendResponse> ToFriendResponse(List<User> friends);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateUserRequestToUser(UpdateUserRequest request, @MappingTarget User entity);
}
