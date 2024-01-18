package com.growth.community.repository;


import com.growth.community.domain.article.Article;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(a.hashTags) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Article> findAllByKeyword(String keyword, Pageable pageable);
}
