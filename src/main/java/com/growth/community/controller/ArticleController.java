package com.growth.community.controller;

import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Void> postArticle(@RequestBody @Valid ArticleDto articleDto) {
        articleService.createArticle(articleDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<List<ArticleDto>> getArticlesByKeyword(@PathVariable String keyword,
                                                                    @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.searchArticlesByKeyword(keyword, pageable));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ArticleWithCommentDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.viewArticleWithComments(id));
    }

    @PutMapping
    public ResponseEntity<Void> putArticle(@RequestBody @Valid ArticleDto articleDto) {
        articleService.updateArticle(articleDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
