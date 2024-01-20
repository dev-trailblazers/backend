package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.validation.HashTags;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ArticleWithCommentDto(
        Long id,
        @NotNull(message = "제목은 필수입니다.") String title,
        @NotNull(message = "내용은 필수입니다.") String content,
        @HashTags(message = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.") String hashtags,
        Set<CommentDto> CommentDtos,    //Comment Entity를 넣었을 때
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
                        .map(CommentDto::fromEntity)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .createdAt(article.getCreatedAt())
                .modifiedAt(article.getModifiedAt())
                .modifiedBy(article.getModifiedBy())
                .build();
    }

}