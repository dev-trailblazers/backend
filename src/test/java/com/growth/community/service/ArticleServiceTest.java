package com.growth.community.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.domain.comment.Comment;
import com.growth.community.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @InjectMocks
    ArticleService articleService;
    @Mock
    ArticleRepository articleRepository;


    @DisplayName("게시글 정보를 입력하면 게시글을 저장한다 - 성공")
    @Test
    void saveArticle_Success() {
        //given
        given(articleRepository.save(any(Article.class))).willReturn(createArticle());
        //when
        articleService.saveArticle(ArticleDto.fromEntity(createArticle()));
        //then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("키워드를 입력하면 제목 또는 해시태그에 키워드가 포함된 게시글 리스트를 반환한다.")
    @Test
    void searchArticlesByKeyword_Success() {
        //given
        List<Article> articles = List.of(
                new Article("제목1", "내용1", "#해시태그"),
                new Article("제목2", "내용2", "#해시태그")
        );
        given(articleRepository.findAllByKeyword(anyString(), any(Pageable.class))).willReturn(articles);
        //when
        List<ArticleDto> articleDtos = articleService.searchArticlesByKeyword("keyword", PageRequest.of(0,10));
        //then
        assertThat(articleDtos.size()).isEqualTo(2);
    }


    @DisplayName("게시글 아이디에 해당하는 게시글과 댓글을 반환한다.")
    @Test
    void getArticleById_Success() {
        //Given
        Long articleId = 1L;
        Article article = createArticle();
        given(articleRepository.findById(articleId)).willReturn(Optional.of(article));
        //When
        ArticleWithCommentDto dto = articleService.getArticleWithCommentsById(articleId);
        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title", article.getTitle())
                .hasFieldOrPropertyWithValue("content", article.getContent())
                .hasFieldOrPropertyWithValue("hashtags", article.getHashtags());
        then(articleRepository).should().findById(articleId);
    }

    @DisplayName("새로운 게시글로 수정한다.")
    @Test
    void updateArticle_Success() {
        //Given
        ArticleDto dto = ArticleDto.builder()
                .id(1L)
                .title("수정된 제목")
                .content("수정된 내용")
                .hashtags("#수정된 해시태그")
                .build();
        Article article = createArticle();
        given(articleRepository.getReferenceById(dto.id())).willReturn(article);
        //When
        articleService.updateArticle(dto);
        //Then
        assertThat(article)
                .hasFieldOrPropertyWithValue("title", dto.title())  //첫번째 파라미터는 article 필드명, 두번째는 기댓값
                .hasFieldOrPropertyWithValue("content", dto.content())
                .hasFieldOrPropertyWithValue("hashtags", dto.hashtags());
        then(articleRepository).should().getReferenceById(dto.id());    //should => 호출되었는지 검증
    }

    @DisplayName("게시글 수정 시 아이디가 누락되면 예외가 발생한다.")
    @Test
    void updateArticle_Fail_NonId() {
        assertThatThrownBy(() -> articleService.updateArticle(createArticleDto()))
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

    private ArticleDto createArticleDto() {
        return ArticleDto.of("title", "content", "#hashtag");
    }

    private Article createArticle() {
        return new Article("title", "content", "#hashtag");
    }
}