package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.repository.IBankAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService extends CommonService<BankAccount, IBankAccountRepository>{
}
