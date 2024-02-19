package com.growth.community.util;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.dto.RequestCommentDto;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.user.dto.Principal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class TestObjectFactory {
    //ENTITY
    public static UserAccount createUserAccount(){
        return UserAccount.builder()
                .id(1L)
                .build();
    }

    public static Article createArticle() {
        return new Article("title", "content", "#hashtag", createUserAccount());
    }

    public static Article createArticleWithComments() {
        return new Article("title", "content", "#hashtag", createUserAccount(), List.of(createComment()));
    }

    public static Comment createComment(){
        return new Comment(
                new Article("title", "content", "#hashtag", createUserAccount()),
                "작성된 댓글"
        );
    }

    //DTO
    public static RequestArticleDto createRequestArticleDto() {
        return RequestArticleDto.of("title", "content", "#hashtag");
    }
    public static ArticleDto createArticleDto() {
        return ArticleDto.of("title", "content", "#hashtag");
    }

    public static ArticleDto createArticleDto(Long id) {
        return ArticleDto.of(1L, "title", "content", "#hashtag");
    }

    public static RequestCommentDto createRequestCommentDto(){
        return RequestCommentDto.of(1L, 1L, "댓글 내용");
    }
    public static CommentDto createCommentDto(){
        return CommentDto.of(1L, 1L, "댓글 내용");
    }

    //USER DETAIL
    public static Principal createPrincipal(){
        return new Principal(
                1L,
                "testuser1@example.com",
                "1234",
                "chany",
                List.of(new SimpleGrantedAuthority(Principal.RoleType.USER.getName()))
        );
    }
}
