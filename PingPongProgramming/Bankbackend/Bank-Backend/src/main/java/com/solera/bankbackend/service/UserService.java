package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.request.UpdateUserRequest;
import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.mapper.UserMapper;
import com.solera.bankbackend.domain.model.Role;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IRoleRepository;
import com.solera.bankbackend.repository.IUserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService extends CommonService<User, IUserRepository> implements UserDetailsService {
    @Autowired
    protected IRoleRepository roleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected IUserRepository userRepository;
    @Autowired
    UserMapper mapper;
    @Autowired
    MyUserDetailsService myUserDetailsService;


    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public UserAccountInformation create(CreateUserRequest request) {
        User user = mapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBalance(20000);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user = repository.save(user);
        return mapper.toUserAccountInformation(user);
    }

    public User getLogged() {
        User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        myUserDetailsService.loadUserByUsername(userLogged.getUsername());
        return userLogged;
    }

    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s, not found", username)));
    }

    public void depositMoney(User user, double balance) {
        user.depositBalance(balance);
        userRepository.save(user);
    }

    public void withdrawMoney(double balance, User user) {
        user.withdrawBalance(balance);
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<User> findByUsernameAndEnabled(String username) {
        return repository.findByUsernameAndEnabled(username, true);
    }

    public UserAccountInformation getUserAccountInformation() {
        User user = this.getLogged();
        return mapper.toUserAccountInformation(user);
    }

    public void addFriend(User user, User friend) {
        user.getFriends().add(friend);
        friend.getFriends().add(user);
        this.save(user);
        this.save(friend);
    }
    public void updateUser(User user, UpdateUserRequest request) {
        mapper.updateUserRequestToUser(request, user);
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        repository.save(user);
    }

    public void deleteUser(User user) {
        user.setEnabled(false);
        repository.save(user);
    }

    public void upgradePremiumUser(User user, double balance, Role role) {
        user.withdrawBalance(balance);
        user.getRoles().add(role);
        repository.save(user);
    }
}