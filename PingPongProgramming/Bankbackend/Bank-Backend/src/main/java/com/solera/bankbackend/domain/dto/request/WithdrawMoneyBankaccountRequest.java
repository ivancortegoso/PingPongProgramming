package com.solera.bankbackend.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawMoneyBankaccountRequest {
    private double Balance;
    private Long senderId;
}
