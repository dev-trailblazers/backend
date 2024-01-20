package com.growth.community.domain.comment.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentDto(
        Long id,
        String content,
        Long articleId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy
) {

    public static CommentDto of(Long id, Long articleId, String content){
        return new CommentDto(id, content, articleId, null, null, null);
    }

    public static CommentDto of(Long articleId, String content){
        return new CommentDto(null, content, articleId, null, null, null);
    }

    public static CommentDto fromEntity(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .articleId(comment.getArticle().getId())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .modifiedBy(comment.getModifiedBy())
                .build();
    }

    public static Comment toEntity(Article article, String content){
        return new Comment(article, content);
    }
}