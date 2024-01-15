package com.code.reviewer.domain.comment;

import com.code.reviewer.domain.AuditingField;
import com.code.reviewer.domain.article.Article;
import jakarta.persistence.*;

@Entity
public class Comment extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(name = "is_removed")
    private boolean isRemoved;
}
