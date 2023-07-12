package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionMiddleResponse;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
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
    public List<TransactionResponse> transactionToTransactionResponse(List<Transaction> transactions) {
        List<TransactionMiddleResponse> transactionMiddleResponses = transactionMapper.transactionToTransactionMiddleResponse(transactions);
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        for (TransactionMiddleResponse transactionMiddleResponse : transactionMiddleResponses) {
            Long id = transactionMiddleResponse.getId();
            Long bankAccountSenderId = transactionMiddleResponse.getSenderID();
            Long bankAccountReceiverId = transactionMiddleResponse.getReceiverID();
            String bankAccountSenderName = "Deleted bank account";
            if (bankAccountService.findById(transactionMiddleResponse.getSenderID()) != null) {
                bankAccountSenderName = bankAccountService.findById(transactionMiddleResponse.getSenderID()).getName();
            }
            String bankAccountReceiverName = "Deleted bank account";
            if (bankAccountService.findById(transactionMiddleResponse.getReceiverID()) != null) {
                bankAccountReceiverName = bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getName();
            }

            double balance = transactionMiddleResponse.getBalance();
            String userSenderName = "Deleted user";
            if(bankAccountService.findById(transactionMiddleResponse.getSenderID()) != null) {
                userSenderName = bankAccountService.findById(transactionMiddleResponse.getSenderID()).getUser().getFirstName() + " " + bankAccountService.findById(transactionMiddleResponse.getSenderID()).getUser().getLastName();
            }
            String userReceiverName = "Deleted user";
            if(bankAccountService.findById(transactionMiddleResponse.getReceiverID()) != null) {
                userReceiverName = bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getUser().getFirstName() + " " + bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getUser().getLastName();
            }
            transactionResponses.add(new TransactionResponse(id, bankAccountSenderId, bankAccountReceiverId, bankAccountSenderName, bankAccountReceiverName,balance, userSenderName, userReceiverName));
        }
        return transactionResponses;
    }
    public TransactionResponse transactionToTransactionResponse(Transaction transaction) {
        TransactionMiddleResponse transactionMiddleResponse = transactionMapper.transactionToTransactionMiddleResponse(transaction);
        Long id = transaction.getId();
        Long bankAccountSenderId = transactionMiddleResponse.getSenderID();
        Long bankAccountReceiverId = transactionMiddleResponse.getReceiverID();
        String bankAccountSenderName = bankAccountService.findById(transactionMiddleResponse.getSenderID()).getName();
        String bankAccountReceiverName = bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getName();
        double balance = transactionMiddleResponse.getBalance();
        String userSenderName = bankAccountService.findById(transactionMiddleResponse.getSenderID()).getUser().getFirstName() + " " + bankAccountService.findById(transactionMiddleResponse.getSenderID()).getUser().getLastName();
        String userReceiverName = bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getUser().getFirstName() + " " + bankAccountService.findById(transactionMiddleResponse.getReceiverID()).getUser().getLastName();
        return new TransactionResponse(id, bankAccountSenderId, bankAccountReceiverId, bankAccountSenderName, bankAccountReceiverName,balance, userSenderName, userReceiverName);
    }

    public List<TransactionResponse> findAllTransactionResponse() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionToTransactionResponse(transactions);
    }

    public void CreateCommentary(Commentary commentary) {
        commentaryService.save(commentary);
    }
}
