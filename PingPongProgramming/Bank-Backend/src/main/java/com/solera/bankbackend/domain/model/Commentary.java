package com.solera.bankbackend.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User writer;

    @ManyToOne
    private Transaction transaction;

    @Column(nullable = false)
    private String commentary;
}
