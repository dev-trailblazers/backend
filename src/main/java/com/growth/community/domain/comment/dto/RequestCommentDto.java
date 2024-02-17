package com.growth.community.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record RequestCommentDto(
        @NotNull Long articleId,
        Long parentCommentId,
        @NotBlank String content
) {

    public static RequestCommentDto of(Long articleId, Long parentCommentId, String content){
        return new RequestCommentDto(articleId, parentCommentId, content);
    }
}