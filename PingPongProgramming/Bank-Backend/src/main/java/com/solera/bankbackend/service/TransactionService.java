package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.DeleteCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CommentaryToCommentaryResponse;
import com.solera.bankbackend.domain.mapper.CreateCommentaryRequestToCommentary;
import com.solera.bankbackend.domain.mapper.TransactionRequestToTransaction;
import com.solera.bankbackend.domain.mapper.TransactionResponseToTransaction;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.repository.ITransactionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    TransactionResponseToTransaction transactionMapper = Mappers.getMapper(TransactionResponseToTransaction.class);
    TransactionRequestToTransaction transactionRequestMapper = Mappers.getMapper(TransactionRequestToTransaction.class);
    @Autowired
    CommentaryService commentaryService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;
    CommentaryToCommentaryResponse commentaryMapper = Mappers.getMapper(CommentaryToCommentaryResponse.class);
    CreateCommentaryRequestToCommentary commentaryRequestMapper = Mappers.getMapper(CreateCommentaryRequestToCommentary.class);

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

    public void userLikeTransaction(Long transactionId) {
        User user = userService.getLogged();
        if (user == null) {
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

    public void createCommentary(CreateCommentaryRequest request) {
        User user = userService.getLogged();
        Transaction t = transactionService.findById(request.getTransactionId());
        if (t != null) {
            Commentary commentary = commentaryRequestMapper.commentaryRequestToCommentary(request);
            commentary.setTransaction(transactionService.findById(request.getTransactionId()));
            commentary.setWriter(user);
            commentaryService.save(commentary);
        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id: " + request.getTransactionId() + " not found");
        }
    }

    public void deleteCommentary(DeleteCommentaryRequest request) {
        User user = userService.getLogged();
        if (commentaryService.findById(request.getId()).getWriter().equals(user) ||
                bankAccountService.findById(commentaryService.findById(request.getId()).getTransaction().getSender().getId()).getUser().equals(user)) {
            commentaryService.deleteById(request.getId());

        } else {
            //TODO
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User logged is not allowed to delete the commentary.");
        }
    }

    public List<TransactionResponse> getTransactionsByUser() {
        User user = userService.getLogged();
        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount b : bankAccountService.findAllByUser(user)) {
            for (Transaction t : b.getTransactionsSentList()) {
                transactions.add(t);
            }
        }
        List<TransactionResponse> result = transactionService.transactionToTransactionResponse(transactions);
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
        List<TransactionResponse> result = transactionService.transactionToTransactionResponse(transactions);
        return result;
    }
    public void CreateTransaction(TransactionRequest request) {
        User user = userService.getLogged();
        BankAccount sender = bankAccountService.findById(request.getSenderID());
        BankAccount receiver = bankAccountService.findById(request.getReceiverID());
        if (sender == null) {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender bank account not found");
        } else if (receiver == null) {
            //TODO
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver bank account not found");
        } else if (!sender.getUser().equals(user)) {
            //TODO
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User logged is not the owner of the sender bank account");
        } else {
            Transaction transaction = transactionRequestMapper.toTransaction(request);
            transaction.setSender(bankAccountService.findById(request.getSenderID()));
            transaction.setReceiver(bankAccountService.findById(request.getReceiverID()));
            if (sender.getBalance() >= request.getBalance()) {
                transactionService.save(transaction);
                sender.getTransactionsSentList().add(transaction);
                sender.setBalance(sender.getBalance() - request.getBalance());
                receiver.setBalance(receiver.getBalance() + request.getBalance());
                bankAccountService.save(sender);
                bankAccountService.save(receiver);
            } else {
                //TODO
                //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough balance");
            }
        }
    }
}
