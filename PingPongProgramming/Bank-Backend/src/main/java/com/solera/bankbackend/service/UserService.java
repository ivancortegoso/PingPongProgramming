package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.mapper.CreateUserRequestToUser;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IRoleRepository;
import com.solera.bankbackend.repository.IUserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
public class UserService extends CommonService<User, IUserRepository> {
    @Autowired
    protected IRoleRepository roleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected IUserRepository userRepository;
    CreateUserRequestToUser mapper = Mappers.getMapper(CreateUserRequestToUser.class);

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User create(CreateUserRequest request) {
        User user = mapper.toUser(request);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.save(user);
        return user;
    }
}
