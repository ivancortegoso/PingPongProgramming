package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateBankAccountRequest;
import com.solera.bankbackend.domain.model.BankAccount;
import org.mapstruct.Mapper;

@Mapper
public abstract class CreateBankAccountRequestToBankAccount {
    public abstract BankAccount toBankAccount(CreateBankAccountRequest request);
}
