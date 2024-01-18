package com.growth.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.growth.community.domain.article.Article;

import java.util.List;
import java.util.Optional;

import com.growth.community.domain.article.dto.ArticleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.PageRequest;
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
        //When
        createArticle();
        long postCount = articleRepository.count();
        //Then
        assertThat(postCount).isEqualTo(1);
    }

    // TODO: - 검색 트랜잭션 readOnly 찾아보기
    @DisplayName("게시글 - 키워드 검색")
    @ValueSource(strings = {"hashTag", "Title"})
    @ParameterizedTest(name = "{index}: {0}")
    void searchArticleByHashTag(String keyword) {
        //Given
        createArticle();
        //When
        List<Article> articles = articleRepository.findAllByKeyword(keyword, PageRequest.of(0,10));
        //Then
        assertThat(articles).hasSize(1);
    }

    @DisplayName("게시글 - 게시글 상세 조회")
    @Test
    void getArticleById() {
        //Given
        createArticle();
        //When
        int count = articleRepository.findAllByKeyword("title", PageRequest.of(0,10)).size();
        //Then
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("게시글 - 게시글 삭제")
    @Test
    void deleteArticleById() {
        //Given
        createArticle();
        //When
        articleRepository.deleteById(1L);
        //Then
        assertThat(articleRepository.findById(1L)).isEmpty();
    }

    private void createArticle(){
        articleRepository.save(
                ArticleDto.toEntity(
                        ArticleDto.builder()
                                .id(1L)
                                .title("title")
                                .content("content")
                                .hashTags("#hashtag")
                                .build()
                ));
    }

    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
        @Bean
        public AuditorAware<Long> auditorAware() {
            return () -> Optional.ofNullable(1L);
        }
    }
}
