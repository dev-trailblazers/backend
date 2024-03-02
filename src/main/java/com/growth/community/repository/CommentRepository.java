package com.growth.community.repository;

import com.growth.community.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserAccount_Id(Long commentId, Long userId);

    Long countByParentCommentId(Long commentId);
}
