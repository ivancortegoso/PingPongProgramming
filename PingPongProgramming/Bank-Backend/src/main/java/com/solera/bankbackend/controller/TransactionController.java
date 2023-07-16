package com.solera.bankbackend.controller;

import com.solera.bankbackend.domain.dto.exceptions.ApiErrorException;
import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.DeleteCommentaryRequest;
import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.model.BankAccount;
import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.domain.model.User;
import com.solera.bankbackend.service.BankAccountService;
import com.solera.bankbackend.service.CommentaryService;
import com.solera.bankbackend.service.TransactionService;
import com.solera.bankbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> getTransactionById(@PathVariable(name = "id") Long transactionId) throws ChangeSetPersister.NotFoundException {
        Transaction transaction = transactionService.findById(transactionId);
        if (transaction == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return ResponseEntity.ok(transactionService.transactionToTransactionResponse(transaction));
    }

    @GetMapping(path = "all")
    @ResponseBody
    public ResponseEntity<?> getTransactionAll() {
        List<TransactionResponse> transactionResponses = transactionService.findAllTransactionResponse();
        return ResponseEntity.ok(transactionResponses);
    }

    @GetMapping(path = "user")
    @ResponseBody
    public ResponseEntity<?> getTransactionUser() {
        List<TransactionResponse> transactionResponsesByUser = transactionService.getTransactionsByUser();
        return ResponseEntity.ok(transactionResponsesByUser);
    }

    @GetMapping(path = "friends")
    @ResponseBody
    public ResponseEntity<?> getTransactionFriends() throws ApiErrorException {
        User user = userService.getLogged();
        if (user.getFriends().size() == 0) {
            throw new ApiErrorException("User has no friends.");
        }
        List<TransactionResponse> transactionResponsesByFriend = transactionService.getTransactionsByFriend();
        return ResponseEntity.ok(transactionResponsesByFriend);
    }

    @PostMapping(path = "")
    @ResponseBody
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) throws ChangeSetPersister.NotFoundException, ApiErrorException {
        User user = userService.getLogged();
        BankAccount sender = bankAccountService.findById(request.getSenderID());
        BankAccount receiver = bankAccountService.findById(request.getReceiverID());
        if (sender == null) {
            throw new ChangeSetPersister.NotFoundException();
        } else if (receiver == null) {
            throw new ChangeSetPersister.NotFoundException();
        } else if(!sender.getUser().equals(user)) {
            throw new ApiErrorException("User logged is not the owner of the sender bank account");
        } else if (sender.getBalance() < request.getBalance()) {
            throw new ApiErrorException("Not enough balance");
        }
        transactionService.CreateTransaction(request);
        return ResponseEntity.ok("Transaction made successfully.");
    }

    @PostMapping(path = "/commentary")
    @ResponseBody
    public ResponseEntity<?> createCommentary(@RequestBody CreateCommentaryRequest request) {
        transactionService.createCommentary(request);
        return ResponseEntity.ok("Commentary created successfully.");
    }

    @DeleteMapping(path = "/commentary")
    @ResponseBody
    public ResponseEntity<?> deleteCommentary(@RequestBody DeleteCommentaryRequest request) {
        transactionService.deleteCommentary(request);
        return ResponseEntity.ok("Commentary deleted");
    }

    @PostMapping(path = "{id}")
    @ResponseBody
    public ResponseEntity<?> likeTransaction(@PathVariable(name = "id") Long transactionId) {
        transactionService.userLikeTransaction(transactionId);
        return ResponseEntity.ok("Like added");
    }
}
