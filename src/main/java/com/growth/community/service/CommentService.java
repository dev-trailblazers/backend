package com.growth.community.service;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.comment.dto.RequestCommentDto;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.CommentRepository;
import com.growth.community.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.ToDoubleBiFunction;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserAccountRepository userAccountRepository;

    public void createComment(RequestCommentDto dto, Long userId) {
        UserAccount userAccount = userAccountRepository.getReferenceById(userId);
        Article article = articleRepository.findById(dto.articleId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글 입니다.")
        );

        Comment comment = new Comment(article, dto.parentCommentId(), dto.content(), userAccount);
        commentRepository.save(comment);
    }

    public void updateComment(String content, Long commentId, Long userId) {
        // TODO: 댓글이 없는 건지, 내가 쓴 글이 아니라서 인지 알 수 없음
        Comment comment = commentRepository.findByIdAndUserAccount_Id(commentId, userId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 수정할 수 없습니다."));
        comment.setContent(content);
    }

    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findByIdAndUserAccount_Id(commentId, userId)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 삭제할 수 없습니다."));
        comment.setRemoved(true);
    }
}
