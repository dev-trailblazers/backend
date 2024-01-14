package com.code.reviewer.repository;


import com.code.reviewer.domain.article.Article;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<Article> findAllByHashTagsContainingIgnoreCase(String hashTag, Pageable pageable);

}
