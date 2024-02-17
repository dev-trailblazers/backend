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

    @ManyToOne(optional = false)
    @JoinColumn(name = "article_id")
    private Article article;

    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    private Long parentCommentId;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    @Column(columnDefinition = "boolean default false")
    private boolean isRemoved;

    public Comment(Article article,String content) {
        this.article = article;
        this.content = content;
    }

    public Comment(Article article, Long parentCommentId, String content) {
        this.article = article;
        this.parentCommentId = parentCommentId;
        this.content = content;
    }
}
