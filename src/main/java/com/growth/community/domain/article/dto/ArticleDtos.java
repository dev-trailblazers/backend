package com.growth.community.domain.article.dto;

import java.util.List;

public record ArticleDtos(
        List<ArticleDto> dtos,
        Long totalCount) {
}
