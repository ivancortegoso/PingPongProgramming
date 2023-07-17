package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.DeleteCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CommentaryMapper;
import com.solera.bankbackend.domain.mapper.TransactionMapper;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.ITransactionRepository;
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
    TransactionMapper transactionMapper;
    @Autowired
    TransactionMapper transactionRequestMapper;
    @Autowired
    CommentaryMapper commentaryMapper;
    @Autowired
    CommentaryService commentaryService;
    @Autowired
    UserService userService;

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

    public void createCommentary(Transaction transaction, User user, CreateCommentaryRequest request) {
        Commentary commentary = commentaryMapper.commentaryRequestToCommentary(request);
        commentary.setTransaction(transaction);
        commentary.setWriter(user);
        commentaryService.save(commentary);
    }

    public void deleteCommentary(DeleteCommentaryRequest request) {
       commentaryService.deleteById(request.getId());
    }

    public List<TransactionResponse> getTransactionsByUser() {
        User user = userService.getLogged();
        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount b : bankAccountService.findAllByUser(user)) {
            for (Transaction t : b.getTransactionsSentList()) {
                transactions.add(t);
            }
        }
        List<TransactionResponse> result = this.transactionToTransactionResponse(transactions);
        return result;
    }

    public List<TransactionResponse> getTransactionsByFriend() {
        User user = userService.getLogged();
        List<Transaction> transactions = new ArrayList<>();
        List<User> friends = user.getFriends();
        for (User u : friends) {
            for (BankAccount b : bankAccountService.findAllByUser(u)) {
                for (Transaction t : b.getTransactionsSentList()) {
                    transactions.add(t);
                }
            }
        }
        List<TransactionResponse> result = this.transactionToTransactionResponse(transactions);
        return result;
    }

    public void CreateTransaction(BankAccount sender, BankAccount receiver, TransactionRequest request) {
        Transaction transaction = transactionRequestMapper.toTransaction(request);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        this.save(transaction);
        sender.getTransactionsSentList().add(transaction);
        sender.setBalance(sender.getBalance() - request.getBalance());
        receiver.setBalance(receiver.getBalance() + request.getBalance());
        bankAccountService.save(sender);
        bankAccountService.save(receiver);
    }
    public void userLikeTransaction(Transaction transaction, User user) {
        transaction.getUsersLiked().add(user);
        repository.save(transaction);
    }
    public void deleteUserLikeTransaction(Transaction transaction, User user) {
        transaction.getUsersLiked().remove(user);
        repository.save(transaction);
    }
}
