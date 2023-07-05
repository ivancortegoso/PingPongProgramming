package com.solera.bankbackend.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

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
        if (obj instanceof Commentary) {
            if (((Commentary) obj).id == this.id) {
                return true;
            }
        }

        return false;
    }
}
