package com.growth.community.repository;

import com.growth.community.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Set<Comment> findByArticle_Id(Long articleId);
}
