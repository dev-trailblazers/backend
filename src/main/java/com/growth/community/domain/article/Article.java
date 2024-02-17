package com.growth.community.domain.article;

import com.growth.community.domain.AuditingField;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.user.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class Article extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtags;

    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount;

    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();


    public Article(String title, String content, String hashtags) {
        this.title = Objects.requireNonNull(title);
        this.content = Objects.requireNonNull(content);
        this.hashtags = hashtags;
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