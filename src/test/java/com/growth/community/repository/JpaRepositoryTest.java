package com.growth.community.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.growth.community.domain.article.Article;

import java.util.List;
import java.util.Optional;

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
import org.springframework.test.context.ActiveProfiles;

@DisplayName("JPA 테스트")
@ActiveProfiles("test")
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
        long preCount = articleRepository.count();
        articleRepository.save(new Article("제목", "본문 내용", "#해시태그"));
        long postCount = articleRepository.count();
        //Then
        assertThat(postCount).isEqualTo(preCount + 1);
    }

    // TODO: - 검색 트랜잭션 readOnly 찾아보기
    @DisplayName("게시글 - 키워드 검색")
    @ValueSource(strings = {"World", "#913612"})
    @ParameterizedTest(name = "{index}: {0}")
    void searchArticleByHashTag(String keyword) {
        //When
        List<Article> articles = articleRepository.findAllByKeyword(keyword, PageRequest.of(0,10));
        //Then
        assertThat(articles).hasSize(1);
    }

    @DisplayName("게시글 - 게시글 상세 조회")
    @Test
    void getArticleById() {
        assertThat(articleRepository.findById(1L)).isPresent();
    }

    @DisplayName("게시글 - 게시글 삭제")
    @Test
    void deleteArticleById() {
        //When
        articleRepository.deleteById(1L);
        //Then
        assertThat(articleRepository.findById(1L)).isEmpty();
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
