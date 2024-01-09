package com.code.reviewer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.code.reviewer.domain.article.Article;
import java.util.List;
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
        List<Article> articles = articleRepository.findAllByTitleContainingIgnoreCase(searchKeyword);
        //Then
        assertThat(articles).hasSize(1);
    }

    @DisplayName("게시글 - 해시태그 검색")
    @Test
    void searchArticleByHashTag() {
        //Given
        String hashTag = "해시태그2";
        articleRepository.save(Article.of("Search Test", "내용", "#해시태그#해시태그2"));
        //When
        List<Article> articles = articleRepository.findAllByHashTagsContainingIgnoreCase(hashTag);
        //Then
        assertThat(articles).hasSize(1);
    }


    @EnableJpaAuditing
    @TestConfiguration
    public static class TestJpaConfig {
    }
}
