package com.code.reviewer.domain.article;

import com.code.reviewer.domain.AuditingField;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


    protected Article() {
    }

    protected Article(String title, String content, String hashTags) {
        this.title = Objects.requireNonNull(title);
        this.content = Objects.requireNonNull(content);
        this.hashTags = hashTags;
    }

    public static Article of(String title, String content, String hashTags) {
        return new Article(title, content, hashTags);
    }
}