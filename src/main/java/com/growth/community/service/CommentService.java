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

    public void saveComment(CommentDto commentDto){
        Article article = articleRepository.getReferenceById(commentDto.id());
        commentRepository.save(new Comment(article, commentDto.content()));
    }

    public void updateComment(CommentDto dto) {
        if(dto.id() == null){
            return;
        }

        try {
            Comment comment = commentRepository.getReferenceById(dto.id());
            comment.setContent(dto.content());
        } catch (EntityNotFoundException e){
            log.warn("댓글 수정 실패. 댓글을 찾을 수 없습니다 - {}", e.getLocalizedMessage());
        }
    }

    public void deleteCommentById(long id) {
        // TODO: 본인 댓글인지 검증
        commentRepository.deleteById(id);
    }
}
