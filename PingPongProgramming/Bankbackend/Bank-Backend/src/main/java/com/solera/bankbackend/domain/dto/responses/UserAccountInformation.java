package com.solera.bankbackend.domain.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User account information")
public class UserAccountInformation {
    @Schema(description = "username", example = "username")
    private String username;
    @Schema(description = "first name", example = "John")
    private String firstName;
    @Schema(description = "last name", example = "Doe")
    private String lastName;
    @Schema(description = "balance in euros", example = "500")
    private double balance;
    @Schema(description = "Friend list", example = "[\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"name\": \"Pau\"\n" +
            "    }\n" +
            "  ]")
    private List<FriendResponse> friends;
}
