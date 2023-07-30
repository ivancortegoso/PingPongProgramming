package com.solera.bankbackend.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private double balance;

    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "receiver")
    private List<Transaction> transactionsReceivedList = new ArrayList<>();
    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactionsSentList = new ArrayList<>();
    private boolean enabled = true;
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof BankAccount) {
            if (((BankAccount) obj).id == this.id) {
                return true;
            }
        }

        return false;
    }
    public void withdrawMoney(double balance) {
        this.setBalance(this.getBalance() - balance);
    }
    public void depositMoney(double balance) {
        this.setBalance(this.getBalance() + balance);
    }
}
