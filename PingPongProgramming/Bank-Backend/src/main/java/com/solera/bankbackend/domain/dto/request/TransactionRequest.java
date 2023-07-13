package com.solera.bankbackend.domain.dto.request;

import com.solera.bankbackend.domain.model.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Long receiverID;
    private Long senderID;
    private double balance;
}
