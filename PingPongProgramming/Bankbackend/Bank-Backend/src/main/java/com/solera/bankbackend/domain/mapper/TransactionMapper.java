package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.TransactionRequest;
import com.solera.bankbackend.domain.dto.responses.TransactionResponse;
import com.solera.bankbackend.domain.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(uses = CommentaryMapper.class, componentModel = "spring")
public abstract class TransactionMapper {
    @Mappings({
            @Mapping(target = "bankAccountSenderId", expression = "java(transaction.getSender().getId())"),
            @Mapping(target = "bankAccountReceiverId", expression = "java(transaction.getReceiver().getId())"),
            @Mapping(target = "bankAccountSenderName", expression = "java(transaction.getSender().getName())"),
            @Mapping(target = "bankAccountReceiverName", expression = "java(transaction.getReceiver().getName())"),
            @Mapping(target = "userSenderName", expression = "java(transaction.getSender().getUser().getFirstName())"),
            @Mapping(target = "userReceiverName", expression = "java(transaction.getReceiver().getUser().getFirstName())"),
            @Mapping(target = "likes", expression = "java(transaction.getUsersLiked().size())")
    })
    public abstract List<TransactionResponse> transactionToTransactionResponse(List<Transaction> transactions);
    @Mappings({
            @Mapping(target = "bankAccountSenderId", expression = "java(transaction.getSender().getId())"),
            @Mapping(target = "bankAccountReceiverId", expression = "java(transaction.getReceiver().getId())"),
            @Mapping(target = "bankAccountSenderName", expression = "java(transaction.getSender().getName())"),
            @Mapping(target = "bankAccountReceiverName", expression = "java(transaction.getReceiver().getName())"),
            @Mapping(target = "userSenderName", expression = "java(transaction.getSender().getUser().getFirstName())"),
            @Mapping(target = "userReceiverName", expression = "java(transaction.getReceiver().getUser().getFirstName())"),
            @Mapping(target = "likes", expression = "java(transaction.getUsersLiked().size())")
    })
    public abstract TransactionResponse transactionToTransactionResponse(Transaction transaction);
    public abstract Transaction toTransaction(TransactionRequest transactionDTO);
}
