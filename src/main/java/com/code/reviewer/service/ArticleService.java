package com.code.reviewer.service;

import com.code.reviewer.domain.article.Article;
import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.repository.ArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleDto articleDto){
        articleRepository.save(ArticleDto.to(articleDto));
    }

    public List<ArticleDto> searchArticlesByTitle(String keyword) {
        List<Article> articles = articleRepository.findAllByTitleContainingIgnoreCase(keyword);
        return articles.stream()
                .map(article -> ArticleDto.from(article))
                .toList();
    }

    public List<ArticleDto> searchArticlesByHashTag(String hashTag) {
        List<Article> articles = articleRepository.findAllByHashTagsContainingIgnoreCase(hashTag);
        return articles.stream()
                .map(article -> ArticleDto.from(article))
                .toList();
    }

    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        return ArticleDto.from(article);
    }

    public void updateArticle(ArticleDto articleDto) {
        if(articleDto.id() == null){
            throw new IllegalArgumentException("게시글을 변경하기 위해서는 해당 게시글의 아이디가 필요합니다.");
        }
        articleRepository.save(ArticleDto.to(articleDto));
    }

    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }
}
