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

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<ArticleDto>> searchArticlesByKeyword(@PathVariable String keyword,
                                                                    @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<ArticleDto> articleDtos = articleService.searchArticlesByKeyword(keyword, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(articleDtos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        ArticleDto articleDto = articleService.getArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).body(articleDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateArticle(@RequestBody @Valid ArticleDto articleDto) {
        // TODO: 유저 아이디로 본인 글인지 검증
        articleService.updateArticle(articleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        // TODO: 유저 아이디로 본인 글인지 검증
        articleService.deleteArticleById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
