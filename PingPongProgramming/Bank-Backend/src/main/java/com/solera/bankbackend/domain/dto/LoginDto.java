package com.solera.bankbackend.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String email;
    private String username;
    private String password;

    @JsonIgnore
    public String getUsernameOrEmail() {
        return this.username == null? this.email: this.username;
    }
}
