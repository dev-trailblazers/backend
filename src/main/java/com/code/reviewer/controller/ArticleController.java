package com.code.reviewer.controller;

import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.service.ArticleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ArticleDto>> searchArticlesByTitle(@PathVariable String title) {
        List<ArticleDto> articleDtos = articleService.searchArticlesByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(articleDtos);
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<ArticleDto>> searchArticlesByHashTag(@PathVariable String hashtag) {
        List<ArticleDto> articleDtos = articleService.searchArticlesByHashTag(hashtag);
        return ResponseEntity.status(HttpStatus.OK).body(articleDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        ArticleDto articleDto = articleService.getArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateArticle(@RequestBody ArticleDto articleDto){
        articleService.updateArticle(articleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id){
        articleService.deleteArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
