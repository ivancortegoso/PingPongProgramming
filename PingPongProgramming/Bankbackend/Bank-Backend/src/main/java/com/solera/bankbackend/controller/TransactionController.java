package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.DeleteCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Commentary;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.CommentaryService;
import com.solera.bankbackend.service.TransactionService;
import com.solera.bankbackend.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transaction/")
@Tag(name = "Transaction", description = "API operations related with transactions")
public class TransactionController {
    @Autowired
    BankAccountService bankAccountService;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    CommentaryService commentaryService;

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable(name = "id") Long transactionId) {
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
            throw new EntityNotFoundException("Transaction not found.");
        }
        return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transaction));
    }

    @GetMapping(path = "all")
    @ResponseBody
    public ResponseEntity<List<TransactionResponse>> getTransactionAll() {
        List<TransactionResponse> transactionResponses = transactionService.findAllTransactionResponse();
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping(path = "user")
    @ResponseBody
    public ResponseEntity<List<TransactionResponse>> getTransactionUser() {
        List<TransactionResponse> transactionResponsesByUser = transactionService.getTransactionsByUser();
        return ResponseEntity.ok(transactionResponsesByUser);
    }

    @GetMapping(path = "friends")
    @ResponseBody
    public ResponseEntity<List<TransactionResponse>> getTransactionFriends() throws ApiErrorException {
        User user = userService.getLogged();
        if (user.getFriends().size() == 0) {
            throw new ApiErrorException("User has no friends.");
        }
        List<TransactionResponse> transactionResponsesByFriend = transactionService.getTransactionsByFriend();
        return ResponseEntity.ok(transactionResponsesByFriend);
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<Void> createTransaction(@RequestBody TransactionRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        BankAccount sender = bankAccountService.findByIdAndEnabled(request.getSenderID());
        BankAccount receiver = bankAccountService.findByIdAndEnabled(request.getReceiverID());
        if (sender == null) {
            throw new EntityNotFoundException("Sender bank account not found.");
        } else if (receiver == null) {
            throw new EntityNotFoundException("Receiver bank account not found.");
        } else if(!sender.getUser().equals(user)) {
            throw new ApiErrorException("User logged is not the owner of the sender bank account");
        } else if (sender.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Not enough balance");
        }
        transactionService.CreateTransaction(sender, receiver, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "commentary")
    @ResponseBody
    public ResponseEntity<Void> createCommentary(@RequestBody CreateCommentaryRequest request) {
        User user = userService.getLogged();
        Transaction transaction = transactionService.findById(request.getTransactionId());
        if(transaction == null) {
            throw new EntityNotFoundException("Transaction not found.");
        }
        transactionService.createCommentary(transaction, user, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "commentary")
    @ResponseBody
    public ResponseEntity<Void> deleteCommentary(@RequestBody DeleteCommentaryRequest request) throws ApiErrorException {
        User user = userService.getLogged();
        Commentary commentary = commentaryService.findById(request.getId());
        User writer = commentary.getWriter();
        if(commentary == null) {
            throw new EntityNotFoundException("Commentary not found");
        } else if (!writer.equals(user) && !commentary.getTransaction().getSender().getUser().equals(user)) {
            throw new ApiErrorException("User logged is not allowed to delete the commentary.");
        }
        transactionService.deleteCommentary(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Void> likeTransaction(@PathVariable(name = "id") Long transactionId) throws ApiErrorException {
        User user = userService.getLogged();
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
            throw new EntityNotFoundException("Transaction not found.");
        } else if (transaction.getUsersLiked().contains(user)) {
            throw new ApiErrorException("User already liked the transaction.");
        }
        transactionService.userLikeTransaction(transaction, user);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteLikeTransaction(@PathVariable(name = "id") Long transactionId) throws ApiErrorException {
        User user = userService.getLogged();
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
            throw new EntityNotFoundException("Transaction not found.");
        } else if (!transaction.getUsersLiked().contains(user)) {
            throw new ApiErrorException("User does not like the transaction.");
        }
        transactionService.deleteUserLikeTransaction(transaction, user);
        return ResponseEntity.ok().build();
    }
}
