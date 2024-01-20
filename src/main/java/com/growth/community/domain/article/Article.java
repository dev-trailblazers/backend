package com.growth.community.domain.article;

import com.growth.community.domain.AuditingField;
import com.growth.community.domain.comment.Comment;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
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

    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    @Setter
    @Column(columnDefinition = "boolean default false")
    private boolean isRemoved;


    // TODO: 왜 연관관계는 생성자 파라미터로 넣지 않을까?
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