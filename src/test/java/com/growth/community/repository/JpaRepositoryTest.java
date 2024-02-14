package com.growth.community.repository;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.user.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 테스트")
@ActiveProfiles("test")
@DataJpaTest
public class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired CommentRepository commentRepository,
                             @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("게시글 - 작성")
    @Test
    void saveArticle() {
        //Given
        long preCount = articleRepository.count();
        Article article = new Article("제목", "본문 내용", "#해시태그");
        //When
        articleRepository.save(article);
        //Then
        long postCount = articleRepository.count();
        assertThat(postCount).isEqualTo(preCount + 1);
    }

    // TODO: - 검색 트랜잭션 readOnly 찾아보기
    @DisplayName("게시글 - 키워드 검색")
    @ValueSource(strings = {"제목으로 검색 - World ", "해시태그로 검색 - #913612"})
    @ParameterizedTest(name = "{index}: {0}")
    void searchArticlesByKeyword(String keywordAndType) {
        //Given
        String keyword = keywordAndType.split(" - ")[1];
        //When
        List<Article> articles = articleRepository.findAllByKeyword(keyword, PageRequest.of(0, 10));
        //Then
        assertThat(articles.size()).isGreaterThan(0);
    }

    @DisplayName("게시글- 게시글과 게시글의 댓글 조회")
    @Test
    void findArticleById() {
        //When
        Article article = articleRepository.findById(1L).get();
        Set<Comment> comments = article.getComments();
        //Then
        assertThat(comments).isNotNull();
        assertThat(article).isNotNull();
    }

    @DisplayName("게시글 - 게시글 수정")
    @Test
    void updateArticle() {
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String modifiedHashtag = "#변경된 해시태그";
        //When
        article.setHashtags(modifiedHashtag);
        Article savedArticle = articleRepository.saveAndFlush(article);
        //Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtags", modifiedHashtag);
    }

    @DisplayName("게시글 - 게시글 삭제")
    @Test
    void deleteArticleById() {
        //When
        articleRepository.deleteById(1L);
        //Then
        assertThat(articleRepository.findById(1L)).isEmpty();
    }


    @DisplayName("댓글 - 댓글 작성")
    @Test
    void saveComment() {
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String content = "댓글 작성";
        Comment comment = new Comment(article, content);
        //When
        Comment savedComment = commentRepository.save(comment);
        //Then
        assertThat(savedComment).hasFieldOrPropertyWithValue("content", content);
    }

    @DisplayName("댓글 - 댓글 수정")
    @Test
    void updateComment() {
        //Given
        Comment comment = commentRepository.findById(1L).orElseThrow();
        String modifiedContent = "댓글 수정";
        //When
        comment.setContent(modifiedContent);
        Comment savedComment = commentRepository.saveAndFlush(comment);
        //Then
        assertThat(savedComment).hasFieldOrPropertyWithValue("content", modifiedContent);
    }

    @DisplayName("댓글 - 댓글 삭제")
    @Test
    void deleteComment() {
        //When
        commentRepository.deleteById(1L);
        //Then
        assertThat(commentRepository.findById(1L)).isEmpty();
    }

    @Test
    void findUserByEmail() {
        Optional<UserAccount> byEmail = userAccountRepository.findByEmail("testuser1@example.com");
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
