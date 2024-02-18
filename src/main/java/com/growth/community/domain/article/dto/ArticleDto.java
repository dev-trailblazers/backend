package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.validation.HashTags;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record ArticleDto(
        Long id,
        @NotBlank(message = ValidationMessage.TITLE_IS_REQUIRED) String title,
        @NotBlank(message = ValidationMessage.CONTENT_IS_REQUIRED) String content,
        @HashTags String hashtags,

        Long userId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {

    public static ArticleDto of(String title, String content, String hashTags){
        return new ArticleDto(null, title, content, hashTags, null,null, null, null);
    }

    public static ArticleDto fromEntity(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .hashtags(article.getHashtags())
                .userId(article.getUserAccount().getId())
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .modifiedBy(article.getModifiedBy())
                .build();
    }

}