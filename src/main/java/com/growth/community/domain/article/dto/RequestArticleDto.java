package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.validation.HashTags;
import jakarta.validation.constraints.NotBlank;


public record RequestArticleDto(
        @NotBlank(message = "제목은 필수입니다.") String title,
        @NotBlank(message = "내용은 필수입니다.") String content,
        @HashTags(message = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.") String hashtags
) {

    public static Article toEntity(RequestArticleDto dto) {
        return new Article(
                dto.title(),
                dto.content(),
                dto.hashtags()
        );
    }
}