package com.solera.bankbackend.domain.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInformation {
    private String username;
    private String firstName;
    private String lastName;
    private double balance;
}
