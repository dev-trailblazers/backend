package com.growth.community.service;

import com.growth.community.Exception.CannotDeleteException;
import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleDtos;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.UserAccountRepository;
import com.growth.community.util.TestObjectFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks ArticleService articleService;
    @Mock ArticleRepository articleRepository;
    @Mock UserAccountRepository userAccountRepository;


    @DisplayName("게시글 정보를 입력하면 게시글을 저장한다 - 성공")
    @Test
    void creatArticle_success() {
        //given
        given(userAccountRepository.getReferenceById(anyLong())).willReturn(TestObjectFactory.createUserAccount());
        given(articleRepository.save(any(Article.class))).willReturn(TestObjectFactory.createArticle());
        //when
        articleService.createArticle(TestObjectFactory.createRequestArticleDto(), 1L);
        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("키워드를 입력하면 제목 또는 해시태그에 키워드가 포함된 게시글 리스트를 반환한다 - 성공")
    @Test
    void searchArticlesByKeyword_success() {
        //given
        List<Article> articles = List.of(
                new Article("제목1", "내용1", "#해시태그", TestObjectFactory.createUserAccount()),
                new Article("제목2", "내용2", "#해시태그", TestObjectFactory.createUserAccount())
        );
        given(articleRepository.findAllByKeyword(anyString(), any(Pageable.class))).willReturn(articles);
        //when
        ArticleDtos articleDtos = articleService.searchArticlesByKeyword("해시태그", PageRequest.of(0,10));
        //then
        assertThat(articleDtos.dtos().size()).isEqualTo(2);
        then(articleRepository).should().findAllByKeyword(anyString(), any(Pageable.class));
    }


    @DisplayName("게시글 아이디에 해당하는 게시글과 댓글을 반환한다 - 성공")
    @Test
    void viewArticleWithComments_success() {
        //Given
        Long articleId = 1L;
        Article article = TestObjectFactory.createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        //When
        ArticleWithCommentDto dto = articleService.inquiryArticleWithComments(articleId);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtags", article.getHashtags());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("새로운 게시글로 수정한다 - 성공")
    @Test
    void updateArticle_success() {
        //Given
        ArticleDto dto = TestObjectFactory.createArticleDto(1L);
        Article article = TestObjectFactory.createArticle();
        given(articleRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(Optional.of(article));
        //When
        articleService.updateArticle(dto, 1L);
        //Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtags", dto.hashtags());
        then(articleRepository).should().findByIdAndUserAccount_Id(dto.id(), 1L);
    }

    @DisplayName("본인이 작성하지 않은 게시글 수정 시 예외가 발생한다.")
    @Test
    void updateArticle_NotOwnArticle_exception() {
        //Given
        given(articleRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(Optional.empty());
        //When & Then
        assertThatThrownBy(() -> articleService.updateArticle(TestObjectFactory.createArticleDto(1L), 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @DisplayName("게시글 아이디에 해당하는 게시글을 삭제한다 - 성공")
    @Test
    void deleteArticle_success() {
        //Given
        given(articleRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(Optional.of(TestObjectFactory.createArticle()));
        //When
        articleService.deleteArticle(1L, 1L);
        //Then
        then(articleRepository).should().deleteById(anyLong());
    }

    @DisplayName("댓글이 달린 게시글을 삭제하면 예외가 발생한다.")
    @Test
    void deleteArticle_includeComments_exception() {
        //Given
        given(articleRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(Optional.of(TestObjectFactory.createArticleWithComments()));
        //When & Then
        assertThatThrownBy(() -> articleService.deleteArticle(1L, 1L))
                .isInstanceOf(CannotDeleteException.class);
    }

    @DisplayName("본인이 작성하지 않은 게시글 삭제 시 예외가 발생한다.")
    @Test
    void deleteArticle_NotOwnArticle_exception() {
        //Given
        given(articleRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(Optional.empty());
        //When & Then
        assertThatThrownBy(() -> articleService.deleteArticle(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}