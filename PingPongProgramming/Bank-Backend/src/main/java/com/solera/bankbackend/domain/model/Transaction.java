package com.solera.bankbackend.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private BankAccount receiver;
    @ManyToOne
    private BankAccount sender;
    @Column(nullable = false)
    private double balance;
    @ManyToMany
    @JoinTable(
            name = "users_likes",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> usersLiked;

    @JsonManagedReference
    @OneToMany(mappedBy = "transaction", fetch = FetchType.LAZY)
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
