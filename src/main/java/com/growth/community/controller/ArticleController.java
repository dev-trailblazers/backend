package com.growth.community.controller;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.domain.article.dto.ResponseArticleDtos;
import com.growth.community.domain.user.dto.Principal;
import com.growth.community.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<Long> postArticle(@RequestBody @Valid RequestArticleDto dto,
                                            @AuthenticationPrincipal Principal principal) {
        Article article = articleService.createArticle(dto, principal.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(article.getId());
    }

    @GetMapping("/keyword")
    public ResponseEntity<ResponseArticleDtos> getArticlesByKeyword(@RequestBody String keyword,
                                                                    @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.searchArticlesByKeyword(keyword, pageable));
    }

    @GetMapping("/id/{articleId}")
    public ResponseEntity<ArticleWithCommentDto> getArticleById(@PathVariable Long articleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(articleService.viewArticleWithComments(articleId));
    }

    @PutMapping("/id/{articleId}")
    public ResponseEntity<Void> putArticle(@RequestBody @Valid RequestArticleDto dto,
                                           @PathVariable Long articleId,
                                           @AuthenticationPrincipal Principal principal) {
        articleService.updateArticle(dto, articleId, principal.id());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
                                              @AuthenticationPrincipal Principal principal) {
        articleService.deleteArticle(articleId, principal.id());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
