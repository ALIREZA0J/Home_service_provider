package com.hsp.home_service_provider.mapper;

import com.hsp.home_service_provider.dto.comment.CommentResponse;
import com.hsp.home_service_provider.dto.comment.CommentSaveRequest;
import com.hsp.home_service_provider.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment commentSaveRequestToModel(CommentSaveRequest request);
    CommentResponse modelToCommentResponse(Comment comment);
}
