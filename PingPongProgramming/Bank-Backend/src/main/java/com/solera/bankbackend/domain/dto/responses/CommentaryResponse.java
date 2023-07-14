package com.solera.bankbackend.domain.dto.responses;

import com.solera.bankbackend.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryResponse {
    private Long id;
    private String writer;
    private String commentary;
}
