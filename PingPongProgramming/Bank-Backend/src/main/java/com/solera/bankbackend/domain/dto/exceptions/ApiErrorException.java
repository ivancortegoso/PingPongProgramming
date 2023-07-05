package com.solera.bankbackend.domain.dto.exceptions;

public class ApiErrorException extends Exception {

    private static final long serialVersionUID = -7544627472383426622L;

    public ApiErrorException(String message) {
        super(message);
    }

}