package com.growth.community.domain.comment.dto;

import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        Long id,
        @NotNull(message = ValidationMessage.ARTICLE_ID_IS_REQUIRED) Long articleId,
        Long parentCommentId,
        @ByteLength(min = 1, max = 1500, message = ValidationMessage.COMMENT_CONTENT_LENGTH) String content,
        Long userId,
        Boolean isRemoved,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {

    public static CommentDto of(Long id, Long articleId, String content){
        return new CommentDto(id, articleId, null, content, null,null, null, null, null);
    }

    public static CommentDto fromEntity(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .articleId(comment.getArticle().getId())
                .parentCommentId(comment.getParentCommentId())
                .userId(comment.getId())
                .isRemoved(comment.isRemoved())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .modifiedBy(comment.getModifiedBy())
                .build();
    }
}