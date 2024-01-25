package com.growth.community.service;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.Comment;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public void createComment(CommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            Comment comment = new Comment(article, dto.content());
            commentRepository.save(comment);
        } catch (EntityNotFoundException e) {
            log.warn("댓글 생성 실패(게시글을 찾을 수 없습니다) - articleId: {} \n {}", dto.articleId(), e.getLocalizedMessage());
        }
    }

    public void updateComment(CommentDto dto) {
        if (dto.id() == null) {
            throw new IllegalArgumentException("댓글을 변경하기 위해서는 해당 댓글의 아이디가 필요합니다.");
        }
        try {
            Comment comment = commentRepository.getReferenceById(dto.id());

            // TODO: 로그인 구현 후 유저 아이디 검사 추가

            comment.setContent(dto.content());
        } catch (EntityNotFoundException e) {
            log.warn("댓글 수정 실패(댓글을 찾을 수 없습니다) - commentId: {} \n {}", dto.id(), e.getLocalizedMessage());
        }
    }

    public void deleteComment(long commentId) {
        try {
            Comment comment = commentRepository.getReferenceById(commentId);

            // TODO: 로그인 구현 후 유저 아이디 검사 추가

            comment.setRemoved(true);
        } catch (EntityNotFoundException e) {
            log.warn("댓글 삭제 실패(댓글을 찾을 수 없습니다) - commentId: {} \n {}", commentId, e.getLocalizedMessage());
        }
    }
}
