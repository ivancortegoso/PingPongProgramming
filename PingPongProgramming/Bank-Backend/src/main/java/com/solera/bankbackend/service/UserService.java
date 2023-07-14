package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateUserRequest;
import com.solera.bankbackend.domain.dto.request.FriendRequest;
import com.solera.bankbackend.domain.dto.responses.FriendResponse;
import com.solera.bankbackend.domain.dto.responses.UserAccountInformation;
import com.solera.bankbackend.domain.mapper.CreateUserRequestToUser;
import com.solera.bankbackend.domain.mapper.UserAccountInformationToUser;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IRoleRepository;
import com.solera.bankbackend.repository.IUserRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService extends CommonService<User, IUserRepository> implements UserDetailsService {
    @Autowired
    protected IRoleRepository roleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    protected IUserRepository userRepository;
    CreateUserRequestToUser mapper = Mappers.getMapper(CreateUserRequestToUser.class);
    UserAccountInformationToUser userAccountInformationToUserMapper = Mappers.getMapper(UserAccountInformationToUser.class);


    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public User create(CreateUserRequest request) {
        User user = mapper.toUser(request);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBalance(20000);
        user = repository.save(user);
        return user;
    }
    public User getLogged() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @Transactional(readOnly = true)
    public User loadUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username: %s, not found", username)));
    }
    public void depositMoney(double balance) {
        User user = this.getLogged();
        user.depositBalance(balance);
        userRepository.save(user);
    }
    public void withdrawMoney(double balance) {
        User user = this.getLogged();
        user.withdrawBalance(balance);
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public UserAccountInformation getUserAccountInformation() {
        User user = this.getLogged();
        return userAccountInformationToUserMapper.toUserAccountInformation(user);
    }
    public void addFriend(FriendRequest request) {
        User user = this.getLogged();
        if(this.findByUsername(request.getUsername()).isPresent()) {
            User friend = this.findByUsername(request.getUsername()).get();
            if (friend.getFriends().contains(user)) {
                //TODO
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users are already friends");
            }
            user.getFriends().add(friend);
            friend.getFriends().add(user);
            this.save(user);
            this.save(friend);
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with username " + request.getUsername() + " does not exist");
        }
    }
}
