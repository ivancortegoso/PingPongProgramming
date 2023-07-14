package com.solera.bankbackend.domain.mapper;

import com.solera.bankbackend.domain.dto.responses.CommentaryResponse;
import com.solera.bankbackend.domain.model.Commentary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public abstract class CommentaryToCommentaryResponse {
    @Mappings({
            @Mapping(target = "writer", expression = "java(commentary.getWriter().getUsername())")
    })
    public abstract CommentaryResponse toCommentaryResponse(Commentary commentary);
    @Mappings({
            @Mapping(target = "writer", expression = "java(commentary.getWriter().getUsername()")
    })
    public abstract List<CommentaryResponse> toCommentaryResponse(List<Commentary> commentaries);
}
