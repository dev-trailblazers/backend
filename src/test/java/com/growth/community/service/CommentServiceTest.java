package com.growth.community.service;

import com.growth.community.domain.article.Article;
import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    ArticleRepository articleRepository;

    @DisplayName("댓글을 입력하면 댓글을 저장한다 - 성공")
    @Test
    void saveComment_Success() {
        //Given
        given(commentRepository.save(any(Comment.class))).willReturn(null);
        //When
        commentService.saveComment(CommentDto.of(1L, "댓글 내용"));
        //Then
        then(commentRepository).should().save(any(Comment.class));
    }

}
