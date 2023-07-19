package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.dto.responses.BankAccountResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class BankAccountMapper {
    public abstract Set<BankAccountResponse> toBankAccountResponse(Set<BankAccount> bankAccounts);
    public abstract BankAccount toBankAccount(CreateBankAccountRequest request);
}
