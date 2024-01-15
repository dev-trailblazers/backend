package com.code.reviewer.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.repository.ArticleRepository;
import java.util.List;
import java.util.Optional;

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

    private final static ArticleDto fixedArticleDto = ArticleDto.of("제목", "내용", "#해시태그", List.of());
    private final static Article fixedArticle = ArticleDto.to(fixedArticleDto);


    @DisplayName("게시글 정보를 입력하면 게시글을 저장한다 - 성공")
    @Test
    void saveArticle_Success() {
        //given
        given(articleRepository.save(any(Article.class))).willReturn(fixedArticle);
        //when
        articleService.saveArticle(ArticleDto.from(fixedArticle));
        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 제목 키워드를 입력하면 제목에 키워드가 포함된 게시글 리스트를 반환한다.")
    @Test
    void searchArticlesByTitle_Success() {
        //given
        List<Article> articles = List.of(
                Article.of("제목1", "내용1", "#해시태그", List.of()),
                Article.of("제목2", "내용2", "#해시태그", List.of())
        );
        given(articleRepository.findAllByTitleContainingIgnoreCase(anyString(), any())).willReturn(articles);
        //when
        List<ArticleDto> articleDtos = articleService.searchArticlesByTitle("keyword", null);
        //then
        assertThat(articleDtos.size()).isEqualTo(articles.size());
    }

    @DisplayName("해시태그를 입력하면 해시태그가 포함된 게시글 리스트를 반환한다.")
    @Test
    void searchArticlesByHashTag_Success() {
        //given
        List<Article> articles = List.of(
                Article.of("제목1", "내용1", "#해시태그1", List.of()),
                Article.of("제목2", "내용2", "#해시태그2", List.of())
        );
        given(articleRepository.findAllByHashTagsContainingIgnoreCase(anyString(), any())).willReturn(articles);
        //when
        List<ArticleDto> articleDtos = articleService.searchArticlesByHashTag("hashTag", null);
        //then
        assertThat(articleDtos.size()).isEqualTo(articles.size());
    }

    @DisplayName("게시글 아이디에 해당하는 게시글을 반환한다.")
    @Test
    void getArticleById_Success() {
        //Given
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(fixedArticle));
        //When
        ArticleDto articleDto = articleService.getArticleById(1L);
        //Then
        assertThat(articleDto.title()).isEqualTo("제목");
    }

    @DisplayName("새로운 게시글로 수정한다.")
    @Test
    void updateArticle_Success() {
        //Given & When
        articleService.updateArticle(ArticleDto.of(1L, "제목", "내용", "해시태그", List.of()));
        //Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("게시글 수정 시 아이디가 누락되면 예외가 발생한다.")
    @Test
    void updateArticle_Fail_NonId() {
        assertThatThrownBy(() -> articleService.updateArticle(fixedArticleDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게시글을 변경하기 위해서는 해당 게시글의 아이디가 필요합니다.");
    }

    @DisplayName("게시글 아이디에 해당하는 게시글을 삭제한다.")
    @Test
    void deleteArticle_Success() {
        //Given & When
        articleService.deleteArticleById(1L);
        //Then
        then(articleRepository).should().deleteById(anyLong());
    }
}