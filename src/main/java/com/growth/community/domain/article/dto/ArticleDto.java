package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.validation.HashTags;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ArticleDto(
        Long id,
        @NotBlank(message = "제목은 필수입니다.") String title,
        @NotBlank(message = "내용은 필수입니다.") String content,
        @HashTags(message = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.") String hashtags,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {

    public static ArticleDto of(String title, String content, String hashTags){
        return new ArticleDto(null, title, content, hashTags, null,null, null, null);
    }

    public static Article toEntity(ArticleDto dto) {
        return new Article(dto.title, dto.content, dto.hashtags);
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