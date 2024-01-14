package com.code.reviewer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.code.reviewer.domain.article.Article;

import java.util.List;

import com.code.reviewer.domain.article.dto.ArticleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@DisplayName("JPA 리포지토리")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class JpaRepositoryTest {
    private final ArticleRepository articleRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @DisplayName("게시글 - 작성")
    @Test
    void saveArticle() {
        //Given
        long preCount = articleRepository.count();
        //When
        articleRepository.save(Article.of("제목", "내용", "#해시태그#해시태그2"));
        long postCount = articleRepository.count();
        //Then
        assertThat(postCount).isEqualTo(preCount + 1);
    }

    @DisplayName("게시글 - 제목 검색")
    @Test
    void searchArticleByTitle() {
        //Given
        String searchKeyword = "search";
        articleRepository.save(Article.of("Search Test", "내용", "#해시태그#해시태그2"));
        //When
        List<Article> articles = articleRepository.findAllByTitleContainingIgnoreCase(searchKeyword, null);
        //Then
        assertThat(articles).hasSize(1);
    }

    // TODO: 1/10/24 - 검색 트랜잭션 readOnly 찾아보기
    @DisplayName("게시글 - 해시태그 검색")
    @Test
    void searchArticleByHashTag() {
        //Given
        String hashTag = "해시태그2";
        articleRepository.save(Article.of("Search Test", "내용", "#해시태그#해시태그2"));
        //When
        List<Article> articles = articleRepository.findAllByHashTagsContainingIgnoreCase(hashTag, null);
        //Then
        assertThat(articles).hasSize(1);
    }

    @DisplayName("게시글 - 게시글 상세 조회")
    @Test
    void getArticleById() {
        //Given
        articleRepository.save(Article.of("Article Detail Test", "내용", "#해시태그#해시태그2"));
        //When
        int count = articleRepository.findAllByTitleContainingIgnoreCase("Article Detail Test", null).size();
        //Then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("게시글 - 게시글 삭제")
    @Test
    void deleteArticleById() {
        //Given
        ArticleDto articleDto = ArticleDto.of(1L, "제목", "내용", "#해시태그");
        articleRepository.save(ArticleDto.to(articleDto));
        //When
        articleRepository.deleteById(1L);
        //Then
        assertThat(articleRepository.findById(1L)).isEmpty();
    }

    // TODO: 1/10/24 - 게시글 생성 중복 없애기
    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
    }
}
