package com.growth.community.service;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.repository.ArticleRepository;
import java.util.List;
import java.util.NoSuchElementException;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleDto articleDto){
        articleRepository.save(ArticleDto.toEntity(articleDto));
    }

    public List<ArticleDto> searchArticlesByTitle(String keyword, Pageable pageable) {
        if(keyword == null || keyword.isEmpty()){
            List<Article> articles = articleRepository.findAll();
            return toDtoList(articles);
        }

        List<Article> articles = articleRepository.findAllByTitleContainingIgnoreCase(keyword, pageable);
        return toDtoList(articles);
    }

    public List<ArticleDto> searchArticlesByHashTag(String hashTag, Pageable pageable) {
        List<Article> articles = articleRepository.findAllByHashTagsContainingIgnoreCase(hashTag, pageable);
        return articles.stream()
                .map(article -> ArticleDto.fromEntity(article))
                .toList();
    }

    public ArticleDto getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        return ArticleDto.fromEntity(article);
    }

    public void updateArticle(ArticleDto dto) {
        if(dto.id() == null){
            throw new IllegalArgumentException("게시글을 변경하기 위해서는 해당 게시글의 아이디가 필요합니다.");
        }
        try{
            // TODO: getReferenceById는 무엇인가?
            Article article = articleRepository.getReferenceById(dto.id());
        /*
            service 계층에서도 검증을 해야하지만 값 존재 여부에 대한 검증은 이미 컨트롤러에서 마쳤다.
            따라서 그외 비즈니스 로직을 검증해야한다.
            ex) 수정하려는 게시글 유저 아이디가 현재 유저와 일치하는지 검증
        */
            // TODO: 로그인 구현 후 유저 아이디 검사 추가

            article.setTitle(dto.title());
            article.setContent(dto.content());
            article.setHashTags(dto.hashTags());
        } catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }

    private List<ArticleDto> toDtoList(List<Article> articles){
        return articles.stream()
                .map(article -> ArticleDto.fromEntity(article))
                .toList();
    }
}
