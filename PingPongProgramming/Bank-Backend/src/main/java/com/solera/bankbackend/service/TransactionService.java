package com.solera.bankbackend.service;

import com.solera.bankbackend.domain.model.Transaction;
import com.solera.bankbackend.repository.ITransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends CommonService<Transaction, ITransactionRepository>{
}
