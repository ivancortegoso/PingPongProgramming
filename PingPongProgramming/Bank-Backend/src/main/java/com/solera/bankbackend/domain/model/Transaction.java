package com.solera.bankbackend.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Long receiverID;
    @Column(nullable = false)
    private Long senderID;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private int likes;

    @OneToMany
    private List<Commentary> commentaries;
}
