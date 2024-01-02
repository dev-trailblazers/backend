package com.code.reviewer.service;

import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void saveArticle(ArticleDto articleDto){
        articleRepository.save(ArticleDto.to(articleDto));
    }
}
