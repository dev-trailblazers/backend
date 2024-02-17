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

    Optional<Article> findByIdAndUserAccount_Id(Long articleId, Long userId);

    @Query("SELECT COUNT(a) FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashtags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Long countByKeyword(@Param("keyword") String keyword);
}
