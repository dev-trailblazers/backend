package com.code.reviewer.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks ArticleService articleService;
    @Mock ArticleRepository articleRepository;

    @DisplayName("게시글 정보를 입력하면 게시글을 저장한다 - 성공")
    @Test
    void saveArticle_Success() {
        Article article = Article.of("제목", "내용", "#해시태그1#해시태그2");
        given(articleRepository.save(any(Article.class))).willReturn(article);

        articleService.saveArticle(ArticleDto.from(article));

        then(articleRepository).should().save(any(Article.class));
    }
}