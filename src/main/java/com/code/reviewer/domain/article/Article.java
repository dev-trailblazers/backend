package com.code.reviewer.domain.article;

import com.code.reviewer.domain.AuditingField;
import com.code.reviewer.domain.comment.Comment;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
@Entity
public class Article extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    private String hashTags;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;


    protected Article() {
    }

    protected Article(String title, String content, String hashTags, List<Comment> comments) {
        this.title = Objects.requireNonNull(title);
        this.content = Objects.requireNonNull(content);
        this.hashTags = hashTags;
        this.comments = comments;
    }

    public static Article of(String title, String content, String hashTags, List<Comment> comments) {
        return new Article(title, content, hashTags, comments);
    }
}