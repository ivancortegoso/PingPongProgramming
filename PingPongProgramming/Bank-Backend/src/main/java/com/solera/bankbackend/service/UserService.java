package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.mapper.CreateUserRequestToUser;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IUserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends CommonService<User, IUserRepository> {

    CreateUserRequestToUser mapper = Mappers.getMapper(CreateUserRequestToUser.class);
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Transactional
    public User create(CreateUserRequest request) {
        User user = mapper.toUser(request);
        user = repository.save(user);

        return user;
    }
}
