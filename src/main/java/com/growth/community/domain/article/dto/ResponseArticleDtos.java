package com.growth.community.domain.article.dto;

import java.util.List;

public record ResponseArticleDtos(
        List<ArticleDto> dtos,
        Long totalCount) {
}
