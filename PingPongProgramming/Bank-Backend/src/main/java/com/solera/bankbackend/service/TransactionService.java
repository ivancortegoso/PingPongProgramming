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
import java.util.Optional;

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
        Transaction t = this.findById(request.getTransactionId());
        if (t != null) {
            Commentary commentary = commentaryMapper.commentaryRequestToCommentary(request);
            commentary.setTransaction(this.findById(request.getTransactionId()));
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
