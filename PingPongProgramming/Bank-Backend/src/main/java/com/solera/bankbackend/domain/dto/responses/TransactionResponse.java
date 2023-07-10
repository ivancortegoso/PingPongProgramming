package com.solera.bankbackend.domain.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long bankAccountSenderId;
    private Long bankAccountReceiverId;
    private String bankAccountSenderName;
    private String bankAccountReceiverName;
    private double balance;
    private String userSenderName;
    private String userReceiverName;
}
