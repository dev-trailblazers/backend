package com.growth.community.service;

import com.growth.community.Exception.CannotDeleteException;
import com.growth.community.Exception.ExceptionMessage;
import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.domain.article.dto.ResponseArticleDtos;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article createArticle(RequestArticleDto dto, Long userId){
        Article article = RequestArticleDto.toEntity(dto);
        article.setUserAccount(new UserAccount(userId));

        return articleRepository.save(article);
    }

    public ResponseArticleDtos searchArticlesByKeyword(String keyword, Pageable pageable) {
        Long totalCount = articleRepository.countByKeyword(keyword);
        List<Article> articles = articleRepository.findAllByKeyword(keyword, pageable);

        List<ArticleDto> articleDtos = articles.stream()
                .map(article -> ArticleDto.fromEntity(article))
                .toList();

        return new ResponseArticleDtos(articleDtos, totalCount);
    }

    public ArticleWithCommentDto viewArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ARTICLE_NOT_FOUND, articleId)));
    }

    public void updateArticle(RequestArticleDto dto, Long articleId, Long userId) {
        Article article = articleRepository.findByIdAndUserAccount_Id(articleId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ARTICLE_NOT_FOUND, articleId)));

        article.setTitle(dto.title());
        article.setContent(dto.content());
        article.setHashtags(dto.hashtags());
    }

    public void deleteArticle(Long articleId, Long userId) {
        Article article = articleRepository.findByIdAndUserAccount_Id(articleId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ExceptionMessage.ARTICLE_NOT_FOUND, articleId)));

        long commentCount = article.getComments()
                    .stream()
                    .filter(comment -> !comment.isRemoved())
                    .count();
        if (commentCount > 0) {
            throw new CannotDeleteException(ExceptionMessage.CANNOT_DELETE_ARTICLE);
        }

        articleRepository.deleteById(articleId);
    }

}
