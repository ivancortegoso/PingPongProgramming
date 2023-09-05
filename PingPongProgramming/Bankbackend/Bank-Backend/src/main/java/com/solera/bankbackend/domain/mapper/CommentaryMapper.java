package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.request.CreateCommentaryRequest;
import com.solera.bankbackend.domain.dto.responses.CommentaryResponse;
import com.solera.bankbackend.domain.model.Commentary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CommentaryMapper {

    @Mapping(target = "writer", expression = "java(commentary.getWriter().getUsername())")

    public abstract CommentaryResponse toCommentaryResponse(Commentary commentary);


    @Mapping(target = "writer", expression = "java(commentary.getWriter().getUsername()")

    public abstract List<CommentaryResponse> toCommentaryResponse(List<Commentary> commentaries);

    public abstract Commentary commentaryRequestToCommentary(CreateCommentaryRequest request);
}
