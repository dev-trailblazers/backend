package com.growth.community.controller;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleWithCommentDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.domain.article.dto.ResponseArticleDtos;
import com.growth.community.domain.user.dto.Principal;
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
    public ResponseEntity<ResponseArticleDtos> getArticlesByKeyword(@PathVariable @Size(min = 1, max = 30, message = "검색 키워드는 1~30자 사이로 입력해주세요.") String keyword,
                                                                    @PageableDefault(sort = "modifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok().body(articleService.searchArticlesByKeyword(keyword, pageable));
    }

    @GetMapping("/id/{articleId}")
    public ResponseEntity<ArticleWithCommentDto> getArticleById(@PathVariable Long articleId) {
        return ResponseEntity.ok().body(articleService.viewArticleWithComments(articleId));
    }

    @PutMapping("/id/{articleId}")
    public ResponseEntity<Void> putArticle(@RequestBody @Valid RequestArticleDto dto,
                                           @PathVariable Long articleId,
                                           @AuthenticationPrincipal Principal principal) {
        articleService.updateArticle(dto, articleId, principal.id());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long articleId,
                                              @AuthenticationPrincipal Principal principal) {
        articleService.deleteArticle(articleId, principal.id());
        return ResponseEntity.ok().build();
    }
}
