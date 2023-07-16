package com.solera.bankbackend.domain.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	private String property;
	private String message;
}