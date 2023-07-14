package com.solera.bankbackend.domain.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.solera.bankbackend.domain.model.Commentary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse implements Serializable {
    private Long id;
    private Long bankAccountSenderId;
    private Long bankAccountReceiverId;
    private String bankAccountSenderName;
    private String bankAccountReceiverName;
    private double balance;
    private String userSenderName;
    private String userReceiverName;
    private int likes;
    private List<CommentaryResponse> commentaries;
}
