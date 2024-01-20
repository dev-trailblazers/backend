package com.growth.community.service;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.Comment;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public void saveComment(CommentDto commentDto){
        Article article = articleRepository.getReferenceById(commentDto.id());
        commentRepository.save(new Comment(article, commentDto.content()));
    }
}
