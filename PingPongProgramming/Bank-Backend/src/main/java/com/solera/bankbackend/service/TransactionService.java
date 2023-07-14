package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CommentaryToCommentaryResponse;
import com.solera.bankbackend.domain.mapper.TransactionResponseToTransaction;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.ITransactionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void userLikeTransaction(User user, Long transactionId) {
        if(user == null) {
            //TODO
        } else {
            Optional<Transaction> t = repository.findById(transactionId);
            if (t.isPresent()) {
                Transaction transaction = t.get();
                if (transaction.getUsersLiked().contains(user)) {
                    //TODO
                } else {
                    transaction.getUsersLiked().add(user);
                    repository.save(transaction);
                }
            } else {
                //TODO
            }
        }
    }

    public void CreateCommentary(Commentary commentary) {
        commentaryService.save(commentary);
    }
}
