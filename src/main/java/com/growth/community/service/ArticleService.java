package com.growth.community.service;

import com.growth.community.Exception.CanNotDeleteException;
import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void createArticle(ArticleDto articleDto){
        articleRepository.save(ArticleDto.toEntity(articleDto));
    }

    public List<ArticleDto> searchArticlesByKeyword(String keyword, Pageable pageable) {
        List<Article> articles = articleRepository.findAllByKeyword(keyword, pageable);
        return articles.stream()
                .map(article -> ArticleDto.fromEntity(article))
                .toList();
    }

    public ArticleWithCommentDto viewArticleWithComments(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentDto::fromEntity)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다 - articleId: " + articleId));
    }

    public void updateArticle(ArticleDto dto) {
        if(dto.id() == null){
            throw new IllegalArgumentException("게시글을 변경하기 위해서는 해당 게시글의 아이디가 필요합니다.");
        }
        try{
            Article article = articleRepository.getReferenceById(dto.id());

            // TODO: 로그인 구현 후 유저 아이디 검사 추가

            article.setTitle(dto.title());
            article.setContent(dto.content());
            article.setHashtags(dto.hashtags());
        } catch (EntityNotFoundException e){
            log.warn("게시글 수정 실패(게시글을 찾을 수 없습니다.) - articleId: {} \n {}", dto.id(), e.getLocalizedMessage());
        }
    }

    public void deleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("게시글 삭제 실패(게시글을 찾을 수 없습니다.) - articleId: " + articleId));

        // TODO: 로그인 구현 후 유저 아이디 검사 추가
        long commentCount = 0;

        if (article.getComments() != null) {
            commentCount = article.getComments()
                    .stream()
                    .filter(comment -> !comment.isRemoved())
                    .count();
        }
        if (commentCount > 0) {
            throw new CanNotDeleteException("댓글이 달린 게시글은 삭제할 수 없습니다.");
        }
        articleRepository.deleteById(articleId);
    }

}
