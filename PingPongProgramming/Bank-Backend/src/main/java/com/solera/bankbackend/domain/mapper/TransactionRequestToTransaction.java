package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper
public abstract class TransactionRequestToTransaction {
    public abstract Transaction toTransaction(TransactionRequest transactionDTO);
}
