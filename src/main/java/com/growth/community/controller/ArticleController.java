package com.growth.community.controller;

import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.service.ArticleService;
import jakarta.validation.Valid;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<ArticleDto>> searchArticlesByTitle(@PathVariable String title,
                                                                  @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ArticleDto> articleDtos = articleService.searchArticlesByTitle(title, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(articleDtos);
    }

    @GetMapping("/hashtag/{hashtag}")
    public ResponseEntity<List<ArticleDto>> searchArticlesByHashTag(@PathVariable String hashtag,
                                                                    @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ArticleDto> articleDtos = articleService.searchArticlesByHashTag(hashtag, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(articleDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto articleDto = articleService.getArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateArticle(@RequestBody @Valid ArticleDto articleDto) {
        articleService.updateArticle(articleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
