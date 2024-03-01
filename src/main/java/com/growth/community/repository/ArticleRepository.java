package com.growth.community.repository;


import com.growth.community.domain.article.Article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashtags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT COUNT(a) FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashtags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Long countByKeyword(@Param("keyword") String keyword);

    Optional<Article> findByIdAndUserAccount_Id(Long articleId, Long userId);

    Long countByUserAccount_Id(Long userId);
    List<Article> findAllByUserAccount_Id(Long userId, Pageable pageable);

<<<<<<< develop
    @Query("SELECT COUNT (a) FROM Article a JOIN FETCH a.comments c JOIN c.userAccount u WHERE u.id = :userId")
    Long countByCommentAndUserId(Long userId);
=======
    @Query("SELECT COUNT(DISTINCT a) FROM Article a JOIN a.comments c JOIN c.userAccount u WHERE u.id = :userId")
    Long countByCommentAndUserId(@Param("userId") Long userId);
>>>>>>> feat: 내가 쓴 게시글, 댓글 단 게시글 조회 구현
    @Query("SELECT DISTINCT a FROM Article a JOIN FETCH a.comments c JOIN c.userAccount u WHERE u.id = :userId")
    List<Article> findAllByCommentAndUserId(Long userId, Pageable pageable);
}
