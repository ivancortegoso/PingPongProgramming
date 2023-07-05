package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.mapper.UserAccountInformationToUser;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.IBankAccountRepository;
import com.solera.bankbackend.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    IBankAccountRepository bankAccountRepository;
    UserAccountInformationToUser userAccountInformationToUserMapper = Mappers.getMapper(UserAccountInformationToUser.class);
    @GetMapping(path = {""})
    @ResponseBody
    public ResponseEntity<?> getUserAccountInformation(@PathVariable(value = "id") Long id) {
        User user = userService.getLogged();
        return ResponseEntity.ok(userAccountInformationToUserMapper.toUserAccountInformation(user));
    }
    @GetMapping(path = "/transaction")
    @ResponseBody
    public ResponseEntity<?> getUserTransactions(@PathVariable(value = "id")Long id) {
        User user = userService.getLogged();
        return ResponseEntity.ok(userAccountInformationToUserMapper.toUserAccountInformation(user));
    }
    /*
    @GetMapping(path = {"", "{id}"})
    @ResponseBody
    public ResponseEntity<?> getTransactions(@PathVariable(value = "id") Long id) {
        List<Transaction> transactions = new ArrayList<>();
        //User user = userService.getLogged();
        User user = null;
        if(user == null) user = userService.findById(id);
        if (user != null) {
            for (BankAccount b : bankAccountRepository.findBankAccountByUser(user)) {
                for (Transaction t : b.getTransactionList()) {
                    transactions.add(t);
                }
            }
        }
        return ResponseEntity.ok(transactions);
    }
     */
}
