package com.code.reviewer.domain.article.dto;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.comment.Comment;
import com.code.reviewer.domain.validation.HashTags;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ArticleDto(
        Long id,
        @NotNull(message = "제목은 필수입니다.") String title,
        @NotNull(message = "내용은 필수입니다.") String content,
        @HashTags(message = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.") String hashTags,
        List<Comment> comments,

        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {

    public static ArticleDto of(String title, String content, String hashTags, List<Comment> comments) {
        return new ArticleDto(null, title, content, hashTags, comments, null, null, null);
    }

    public static ArticleDto of(Long id, String title, String content, String hashTags, List<Comment> comments) {
        return new ArticleDto(id, title, content, hashTags, comments, null, null, null);
    }

    public static ArticleDto of(Long id, String title, String content, String hashTags, List<Comment> comments,
                                LocalDateTime createdAt, LocalDateTime modifiedAt, Long modifiedBy) {
        return new ArticleDto(id, title, content, hashTags, comments, createdAt, modifiedAt, modifiedBy);
    }

    public static Article to(ArticleDto articleDto) {
        return Article.of(articleDto.title, articleDto.content, articleDto.hashTags, articleDto.comments);
    }

    public static ArticleDto from(Article article) {
        return ArticleDto.of(article.getId(), article.getTitle(), article.getContent(), article.getHashTags(),
                article.getComments(), article.getCreatedAt(), article.getModifiedAt(), article.getModifiedBy());
    }

}