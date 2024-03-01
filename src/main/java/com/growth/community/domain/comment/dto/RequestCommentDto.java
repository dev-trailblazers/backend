package com.growth.community.domain.comment.dto;

import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RequestCommentDto(
        @NotNull(message = ValidationMessage.ARTICLE_ID_IS_REQUIRED) Long articleId,
        Long parentCommentId,
        @ByteLength(min = 1, max = 1500, message = ValidationMessage.COMMENT_CONTENT_LENGTH) String content
) {

    public static RequestCommentDto of(Long articleId, Long parentCommentId, String content) {
        return new RequestCommentDto(articleId, parentCommentId, content);
    }
}