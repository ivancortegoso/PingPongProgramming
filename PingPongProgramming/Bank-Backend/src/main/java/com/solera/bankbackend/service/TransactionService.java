package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.responses.TransactionMiddleResponse;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CommentaryToCommentaryResponse;
import com.solera.bankbackend.domain.mapper.TransactionMiddleResponseToTransaction;
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
    TransactionMiddleResponseToTransaction transactionMapper = Mappers.getMapper(TransactionMiddleResponseToTransaction.class);
    @Autowired
    CommentaryService commentaryService;
    @Autowired
    CommentaryToCommentaryResponse commentaryMapper = Mappers.getMapper(CommentaryToCommentaryResponse.class);
    public List<TransactionResponse> transactionToTransactionResponse(List<Transaction> transactions) {
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (Transaction t : transactions) {
            transactionResponses.add(transactionToTransactionResponse(t));
        }
        return transactionResponses;
    }
    public TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        TransactionMiddleResponse transactionMiddleResponse = transactionMapper.transactionToTransactionMiddleResponse(transaction);
        Long id = transactionMiddleResponse.getId();
        Long bankAccountSenderId = transaction.getSender().getId();
        Long bankAccountReceiverId = transaction.getReceiver().getId();
        String bankAccountSenderName = "Deleted bank account";
        int likes = transactionMiddleResponse.getLikes();
        List<Commentary> commentaries = transactionMiddleResponse.getCommentaries();
        if (bankAccountService.findById(transaction.getSender().getId()) != null) {
            bankAccountSenderName = bankAccountService.findById(transaction.getSender().getId()).getName();
        }
        String bankAccountReceiverName = "Deleted bank account";
        if (bankAccountService.findById(transaction.getReceiver().getId()) != null) {
            bankAccountReceiverName = bankAccountService.findById(transaction.getReceiver().getId()).getName();
        }

        double balance = transactionMiddleResponse.getBalance();
        String userSenderName = "Deleted user";
        if(bankAccountService.findById(transaction.getSender().getId()) != null) {
            userSenderName = bankAccountService.findById(transaction.getSender().getId()).getUser().getFirstName() + " " + bankAccountService.findById(transaction.getSender().getId()).getUser().getLastName();
        }
        String userReceiverName = "Deleted user";
        if(bankAccountService.findById(transaction.getReceiver().getId()) != null) {
            userReceiverName = bankAccountService.findById(transaction.getReceiver().getId()).getUser().getFirstName() + " " + bankAccountService.findById(transaction.getReceiver().getId()).getUser().getLastName();
        }
        return new TransactionResponse(id, bankAccountSenderId, bankAccountReceiverId, bankAccountSenderName, bankAccountReceiverName,balance, userSenderName, userReceiverName, likes, commentaryMapper.toCommentaryResponse(commentaries));
    }

    public List<TransactionResponse> findAllTransactionResponse() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionToTransactionResponse(transactions);
    }

    public void CreateCommentary(Commentary commentary) {
        commentaryService.save(commentary);
    }
}
