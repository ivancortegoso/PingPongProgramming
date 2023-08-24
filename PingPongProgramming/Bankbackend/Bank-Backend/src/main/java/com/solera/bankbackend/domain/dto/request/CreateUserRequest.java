package com.solera.bankbackend.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest implements Serializable {
    @NotNull(message = "First name cannot be null")
    private String firstName;
    private String lastName;
    private String birthDate;
    private int phoneNumber;
    private String address;
    private String documentId;
    private String username;
    private String password;
    private String email;

}