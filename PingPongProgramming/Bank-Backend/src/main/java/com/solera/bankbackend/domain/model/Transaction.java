package com.solera.bankbackend.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Commentary> commentaries = new ArrayList<>();
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
        if (obj instanceof Transaction) {
            if (((Transaction) obj).id == this.id) {
                return true;
            }
        }

        return false;
    }
}
