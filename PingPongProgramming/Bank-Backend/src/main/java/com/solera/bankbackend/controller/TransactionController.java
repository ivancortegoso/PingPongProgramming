package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.DeleteCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.mapper.CreateCommentaryRequestToCommentary;
import com.solera.bankbackend.domain.mapper.TransactionRequestToTransaction;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.CommentaryService;
import com.solera.bankbackend.service.TransactionService;
import com.solera.bankbackend.service.UserService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/transaction/")
public class TransactionController {
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    CommentaryService commentaryService;
    TransactionRequestToTransaction transactionMapper = Mappers.getMapper(TransactionRequestToTransaction.class);
    CreateCommentaryRequestToCommentary commentaryMapper = Mappers.getMapper(CreateCommentaryRequestToCommentary.class);

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> getTransactionById(@PathVariable(name = "id") Long transactionId) {
        if (!transactionService.findById(transactionId).equals(null)) {
            Transaction transaction = transactionService.findById(transactionId);
            return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transaction));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }
    }

    @GetMapping(path = "all")
    @ResponseBody
    public ResponseEntity<?> getTransactionAll() {
        return ResponseEntity.ok(transactionService.findAllTransactionResponse());
    }

    @GetMapping(path = "user")
    @ResponseBody
    public ResponseEntity<?> getTransactionUser() {
        User user = userService.getLogged();
        List<Transaction> transactions = new ArrayList<>();
        for (BankAccount b : bankAccountService.findAllByUser(user)) {
            for (Transaction t : b.getTransactionsSentList()) {
                transactions.add(t);
            }
        }
        List<TransactionResponse> result = transactionService.transactionToTransactionResponse(transactions);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "friends")
    @ResponseBody
    public ResponseEntity<?> getTransactionFriends() {
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
        return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transactions));
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        User user = userService.getLogged();
        BankAccount sender = bankAccountService.findById(request.getSenderID());
        BankAccount receiver = bankAccountService.findById(request.getReceiverID());
        if (sender == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender bank account not found");
        } else if (receiver == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver bank account not found");
        } else if (!sender.getUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User logged is not the owner of the sender bank account");
        } else {
            Transaction transaction = transactionMapper.toTransaction(request);
            transaction.setSender(bankAccountService.findById(request.getSenderID()));
            transaction.setReceiver(bankAccountService.findById(request.getReceiverID()));
            if (sender.getBalance() >= request.getBalance()) {
                transactionService.save(transaction);
                sender.getTransactionsSentList().add(transaction);
                sender.setBalance(sender.getBalance() - request.getBalance());
                receiver.setBalance(receiver.getBalance() + request.getBalance());
                bankAccountService.save(sender);
                bankAccountService.save(receiver);
                return ResponseEntity.ok(sender.getBalance());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough balance");
            }
        }
    }

    @PostMapping(path = "/commentary")
    @ResponseBody
    public ResponseEntity<?> createCommentary(@RequestBody CreateCommentaryRequest request) {
        User user = userService.getLogged();
        Transaction t = transactionService.findById(request.getTransactionId());
        if (t != null) {
            Commentary commentary = commentaryMapper.commentaryRequestToCommentary(request);
            commentary.setTransaction(transactionService.findById(request.getTransactionId()));
            commentary.setWriter(user);
            transactionService.CreateCommentary(commentary);
            return ResponseEntity.ok().body("Commentary created");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction with id: " + request.getTransactionId() + " not found");
        }
    }

    @DeleteMapping(path = "/commentary")
    @ResponseBody
    public ResponseEntity<?> deleteCommentary(@RequestBody DeleteCommentaryRequest request) {
        User user = userService.getLogged();
        if (commentaryService.findById(request.getId()).getWriter().equals(user) ||
                bankAccountService.findById(commentaryService.findById(request.getId()).getTransaction().getSender().getId()).getUser().equals(user)) {
            commentaryService.deleteById(request.getId());
            return ResponseEntity.ok("Commentary deleted");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User logged is not allowed to delete the commentary.");
        }
    }

    @PostMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> likeTransaction(@PathVariable(name = "id") Long transactionId) {
        User user = userService.getLogged();
        transactionService.userLikeTransaction(user, transactionId);
        return ResponseEntity.ok("Like added");
    }
}
