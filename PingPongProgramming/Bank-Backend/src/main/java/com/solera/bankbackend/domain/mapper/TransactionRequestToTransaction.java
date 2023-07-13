package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class TransactionRequestToTransaction {

    public abstract Transaction toTransaction(TransactionRequest transactionDTO);
}
