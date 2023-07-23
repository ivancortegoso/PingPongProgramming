package com.solera.bankbackend.domain.dto.request;

import java.util.Date;

public class CreateUserRequestBuilder {
    private String firstName;
    private String lastName;
    private String birthDate;
    private int phoneNumber;
    private String address;
    private String documentId;
    private String username;
    private String password;
    private String email;

    public CreateUserRequestBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CreateUserRequestBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CreateUserRequestBuilder setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public CreateUserRequestBuilder setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public CreateUserRequestBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public CreateUserRequestBuilder setDocumentId(String documentId) {
        this.documentId = documentId;
        return this;
    }

    public CreateUserRequestBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserRequestBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequestBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserRequest createCreateUserRequest() {
        return new CreateUserRequest(firstName, lastName, birthDate, phoneNumber, address, documentId, username, password, email);
    }
}