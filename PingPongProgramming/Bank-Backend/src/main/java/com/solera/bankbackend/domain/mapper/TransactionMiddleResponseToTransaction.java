package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.responses.TransactionMiddleResponse;
import com.solera.bankbackend.domain.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public abstract class TransactionMiddleResponseToTransaction {
    public abstract List<TransactionMiddleResponse> transactionToTransactionMiddleResponse(List<Transaction> transactions);
    public abstract TransactionMiddleResponse transactionToTransactionMiddleResponse(Transaction transaction);
}
