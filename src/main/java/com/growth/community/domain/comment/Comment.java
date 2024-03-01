package com.growth.community.domain.comment;

import com.growth.community.domain.AuditingField;
import com.growth.community.domain.article.Article;
import com.growth.community.domain.user.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    private Long parentCommentId;

    @Setter
    @Column(nullable = false, length = 1500)
    private String content;

    @Setter
    @Column(columnDefinition = "boolean default false")
    private boolean isRemoved;

    public Comment(Article article,String content) {
        this.article = article;
        this.content = content;
    }

    public Comment(Article article, Long parentCommentId, String content, UserAccount userAccount) {
        this.article = article;
        this.parentCommentId = parentCommentId;
        this.content = content;
        this.userAccount = userAccount;
    }
}
