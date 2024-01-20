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


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @DisplayName("댓글 내용을 입력하면 댓글을 수정한다 - 성공")
    @Test
    void updateComment_Success() {
        //Given
        Comment comment = createComment();
        CommentDto dto = CommentDto.of(1L, 1L, "수정된 댓글");
        given(commentRepository.getReferenceById(dto.id())).willReturn(comment);
        //When
        commentService.updateComment(dto);
        //Then
        assertThat(comment.getContent())
                .isEqualTo(dto.content());
        then(commentRepository).should().getReferenceById(dto.id());
    }

    private Comment createComment(){
        return new Comment(new Article("title", "content", "#hashtag"), "작성된 댓글");
    }
}
