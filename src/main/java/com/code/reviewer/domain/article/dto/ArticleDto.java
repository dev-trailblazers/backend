package com.code.reviewer.domain.article.dto;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.validation.HashTags;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        @NotNull(message = "제목은 필수입니다.") String title,
        @NotNull(message = "내용은 필수입니다.") String content,
        @HashTags(message = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.") String hashTags,

        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ArticleDto of(String title, String content, String hashTags) {
        return new ArticleDto(null, title, content, hashTags, null, null);
    }

    public static ArticleDto of(Long id, String title, String content, String hashTags, LocalDateTime createdAt,
                                LocalDateTime modifiedAt) {
        return new ArticleDto(id, title, content, hashTags, createdAt, modifiedAt);
    }

    public static Article to(ArticleDto articleDto) {
        return Article.of(articleDto.title, articleDto.content, articleDto.hashTags);
    }

    public static ArticleDto from(Article article) {
        return ArticleDto.of(article.getId(), article.getTitle(), article.getContent(), article.getHashTags(),
                article.getCreatedAt(), article.getModifiedAt());
    }

}