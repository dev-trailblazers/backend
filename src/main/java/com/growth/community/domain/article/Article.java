package com.growth.community.domain.article;

import com.growth.community.domain.AuditingField;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.user.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@NoArgsConstructor
@Entity
public class Article extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 300)
    private String title;

    @Setter
    @Column(nullable = false, length = 3000)
    private String content;

    @Setter
    @Column(length = 200)
    private String hashtags;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @OrderBy("id")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();


    public Article(String title, String content, String hashtags, UserAccount userAccount) {
        this.title = Objects.requireNonNull(title);
        this.content = Objects.requireNonNull(content);
        this.hashtags = hashtags;
        this.userAccount = userAccount;
    }

    public Article(String title, String content, String hashtags, UserAccount userAccount, List comments) {
        this(title, content, hashtags, userAccount);
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}