package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CommentaryToCommentaryResponse;
import com.solera.bankbackend.domain.mapper.TransactionResponseToTransaction;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.repository.ITransactionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService extends CommonService<Transaction, ITransactionRepository> {
    @Autowired
    ITransactionRepository transactionRepository;
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    TransactionResponseToTransaction transactionMapper = Mappers.getMapper(TransactionResponseToTransaction.class);
    @Autowired
    CommentaryService commentaryService;
    @Autowired
    CommentaryToCommentaryResponse commentaryMapper = Mappers.getMapper(CommentaryToCommentaryResponse.class);
    public List<TransactionResponse> transactionToTransactionResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = transactionMapper.transactionToTransactionResponse(transactions);
        return transactionResponses;
    }
    public TransactionResponse transactionToTransactionResponse(Transaction transactions) {
        TransactionResponse transactionResponses = transactionMapper.transactionToTransactionResponse(transactions);
        return transactionResponses;
    }
    public List<TransactionResponse> findAllTransactionResponse() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionToTransactionResponse(transactions);
    }

    public void CreateCommentary(Commentary commentary) {
        commentaryService.save(commentary);
    }
}
