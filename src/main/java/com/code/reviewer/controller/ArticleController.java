package com.code.reviewer.controller;

import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.service.ArticleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Void> saveArticle(@RequestBody @Valid ArticleDto articleDto) {
        articleService.saveArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<ArticleDto>> searchArticleByTitle(@PathVariable String title) {
        articleService.searchArticlesByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<ArticleDto>> searchArticleByHashTag(@PathVariable String hashtag) {
        articleService.searchArticlesByHashTag(hashtag);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        ArticleDto articleDto = articleService.getArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }
}
