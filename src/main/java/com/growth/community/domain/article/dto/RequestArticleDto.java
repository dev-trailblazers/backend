package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.validation.HashTags;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.NotBlank;


public record RequestArticleDto(
        @NotBlank(message = ValidationMessage.TITLE_IS_REQUIRED) String title,
        @NotBlank(message = ValidationMessage.CONTENT_IS_REQUIRED) String content,
        @HashTags String hashtags
) {

    public Article toEntity(UserAccount userAccount) {
        return new Article(
                this.title,
                this.content,
                this.hashtags,
                userAccount
        );
    }

    public static RequestArticleDto of(String title, String content, String hashtags){
        return new RequestArticleDto(title, content, hashtags);
    }
}