package com.growth.community.domain.article.dto;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.HashTags;
import com.growth.community.domain.validation.ValidationMessage;


public record RequestArticleDto(
        @ByteLength(min = 1, max = 300, message = ValidationMessage.ARTICLE_TITLE_LENGTH) String title,
        @ByteLength(min = 1, max = 3000, message = ValidationMessage.ARTICLE_CONTENT_LENGTH) String content,
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