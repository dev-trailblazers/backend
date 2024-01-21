package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.dto.CommentDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ArticleWithCommentDto(
        Long id,
        String title,
        String content,
        String hashtags,
        Set<CommentDto> CommentDtos,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        Long modifiedBy) {
    public static ArticleWithCommentDto fromEntity(Article article) {
        return ArticleWithCommentDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .hashtags(article.getHashtags())
                .CommentDtos(Optional.ofNullable(article.getComments())
                        .orElseGet(Collections::emptySet)
                        .stream()
                        .filter(comment -> !comment.isRemoved())
                        .map(CommentDto::fromEntity)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .modifiedBy(article.getModifiedBy())
                .build();
    }

}