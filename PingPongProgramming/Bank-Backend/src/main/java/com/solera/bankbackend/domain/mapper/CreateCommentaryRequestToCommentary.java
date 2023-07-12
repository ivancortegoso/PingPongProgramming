package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.model.Commentary;
import org.mapstruct.Mapper;

@Mapper
public abstract class CreateCommentaryRequestToCommentary {
    public abstract Commentary commentaryRequestToCommentary(CreateCommentaryRequest request);
}
