package com.growth.community.controller;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.domain.article.dto.ArticleDtos;
import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.validation.ValidationMessage;
import com.growth.community.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/")
    public ResponseEntity<Void> postArticle(@RequestBody @Valid RequestArticleDto dto,
                                            @AuthenticationPrincipal Principal principal) {
        Article article = articleService.createArticle(dto, principal.id());
        return ResponseEntity.created(URI.create("/articles/id/" + article.getId())).build();
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<ArticleDtos> getArticlesByKeyword(@PathVariable @Size(min = 1, max = 30, message = ValidationMessage.KEYWORD_LENGTH) String keyword,
                                                            @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(articleService.searchArticlesByKeyword(keyword, pageable));
    }

    @GetMapping("/id/{articleId}")
    public ResponseEntity<ArticleWithCommentDto> getArticleById(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(articleService.viewArticleWithComments(articleId));
    }

    @PutMapping("/id")
    public ResponseEntity<Void> putArticle(@RequestBody @Valid ArticleDto dto,
                                           @AuthenticationPrincipal Principal principal) {
        articleService.updateArticle(dto, principal.id());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
                                              @AuthenticationPrincipal Principal principal) {
        articleService.deleteArticle(articleId, principal.id());
        return ResponseEntity.ok().build();
    }
}
