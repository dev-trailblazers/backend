package com.growth.community.domain.comment.dto;

import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RequestCommentDto(
        @NotNull(message = ValidationMessage.ARTICLE_ID_IS_REQUIRED) Long articleId,
        Long parentCommentId,
        @NotBlank(message = ValidationMessage.CONTENT_IS_REQUIRED) String content
) {

    public static RequestCommentDto of(Long articleId, Long parentCommentId, String content){
        return new RequestCommentDto(articleId, parentCommentId, content);
    }
}