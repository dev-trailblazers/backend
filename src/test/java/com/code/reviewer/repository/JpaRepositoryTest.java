package com.code.reviewer.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.code.reviewer.domain.article.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    void saveArticle_Success() {
        //Given
        long preCount = articleRepository.count();
        //When
        articleRepository.save(Article.of("제목", "내용", "#해시태그#해시태그2"));
        long postCount = articleRepository.count();
        //Then
        assertThat(postCount).isEqualTo(preCount + 1);
    }

}
