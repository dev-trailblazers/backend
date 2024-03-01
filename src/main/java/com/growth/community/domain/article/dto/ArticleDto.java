package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.HashTags;
import com.growth.community.domain.validation.ValidationMessage;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record ArticleDto(
        Long id,
        @ByteLength(min = 1, max = 300, message = ValidationMessage.ARTICLE_TITLE_LENGTH)
        String title,
        @ByteLength(min = 1, max = 3000, message = ValidationMessage.ARTICLE_CONTENT_LENGTH) String content,
        @HashTags String hashtags,
        int commentCount,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {

    public static ArticleDto of(String title, String content, String hashTags){
        return new ArticleDto(null, title, content, hashTags, 0, null,null, null, null);
    }

    public static ArticleDto of(Long id, String title, String content, String hashTags){
        return new ArticleDto(id, title, content, hashTags, 0, null,null, null, null);
    }

    public static ArticleDto fromEntity(Article article) {
        return ArticleDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .hashtags(article.getHashtags())
                .userId(article.getUserAccount().getId())
                .commentCount(article.getComments().size())
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .modifiedBy(article.getModifiedBy())
                .build();
    }

}