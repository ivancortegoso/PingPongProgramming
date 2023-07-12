package com.solera.bankbackend.domain.dto.responses;

import lombok.Data;

@Data
public class BankAccountResponse {
    private Long id;
    private String name;
    private double balance;
}
