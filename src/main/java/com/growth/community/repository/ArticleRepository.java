package com.growth.community.repository;


import com.growth.community.domain.article.Article;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT COUNT(a) FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashtags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Long countByKeyword(@Param("keyword") String keyword);
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashtags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Long countByUserAccount_Id(Long userId);
    List<Article> findAllByUserAccount_Id(Long userId, Pageable pageable);
    Optional<Article> findByIdAndUserAccount_Id(Long articleId, Long userId);

<<<<<<< develop
<<<<<<< develop
<<<<<<< develop
=======
<<<<<<< b717eca99bd243f2fa8e8baebaac66d888736781
=======
<<<<<<< develop
<<<<<<< develop
>>>>>>> feat: 게시글 목록 조회 시 게시글에 해당하는 댓글 수 추가
>>>>>>> feat: 게시글 목록 조회 시 게시글에 해당하는 댓글 수 추가
    @Query("SELECT COUNT (a) FROM Article a JOIN FETCH a.comments c JOIN c.userAccount u WHERE u.id = :userId")
    Long countByCommentAndUserId(Long userId);
=======
    @Query("SELECT COUNT(DISTINCT a) FROM Article a JOIN a.comments c JOIN c.userAccount u WHERE u.id = :userId")
    Long countByCommentAndUserId(@Param("userId") Long userId);
>>>>>>> feat: 내가 쓴 게시글, 댓글 단 게시글 조회 구현
    @Query("SELECT DISTINCT a FROM Article a JOIN FETCH a.comments c JOIN c.userAccount u WHERE u.id = :userId")
=======
    @Query("SELECT COUNT(DISTINCT a) FROM Article a JOIN a.comments c WHERE c.userAccount.id = :userId")
    Long countByCommentAndUserId(@Param("userId") Long userId);
    @Query("SELECT DISTINCT a FROM Article a JOIN FETCH a.comments c WHERE a.userAccount.id = :userId")
>>>>>>> feat: 게시글 목록 조회 시 게시글에 해당하는 댓글 수 추가
    List<Article> findAllByCommentAndUserId(Long userId, Pageable pageable);
}
