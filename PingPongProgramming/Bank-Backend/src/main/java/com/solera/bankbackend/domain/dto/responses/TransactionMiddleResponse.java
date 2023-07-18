package com.solera.bankbackend.domain.dto.responses;

import com.solera.bankbackend.domain.model.Commentary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionMiddleResponse {
    private Long id;
    private double balance;
    private int likes;
    private List<Commentary> commentaries;
}
