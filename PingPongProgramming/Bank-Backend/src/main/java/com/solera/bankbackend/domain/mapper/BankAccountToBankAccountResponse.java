package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public abstract class BankAccountToBankAccountResponse {
    public abstract Set<BankAccountResponse> toBankAccountResponse(Set<BankAccount> bankAccounts);
}
