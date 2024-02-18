package com.growth.community.service;

import com.growth.community.domain.comment.Comment;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.repository.ArticleRepository;
import com.growth.community.repository.CommentRepository;
import com.growth.community.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.growth.community.util.TestObjectFactory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock CommentRepository commentRepository;
    @Mock ArticleRepository articleRepository;
    @Mock UserAccountRepository userAccountRepository;

    @DisplayName("댓글을 입력하면 댓글을 저장한다 - 성공")
    @Test
    void createComment_success() {
        //Given
        given(commentRepository.save(any(Comment.class)))
                .willReturn(null);
        given(articleRepository.findById(anyLong()))
                .willReturn(Optional.of(TestObjectFactory.createArticle()));
        given(userAccountRepository.getReferenceById(anyLong()))
                .willReturn(TestObjectFactory.createUserAccount());
        //When
        commentService.createComment(TestObjectFactory.createRequestCommentDto(), 1L);
        //Then
        then(commentRepository).should().save(any(Comment.class));
    }

    @DisplayName("댓글 내용을 입력하면 댓글을 수정한다 - 성공")
    @Test
    void updateComment_success() {
        //Given
        Optional<Comment> comment = Optional.of(TestObjectFactory.createComment());
        CommentDto dto = TestObjectFactory.createCommentDto();
        given(commentRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(comment);
        //When
        commentService.updateComment(dto, 1L);
        //Then
        then(commentRepository).should().findByIdAndUserAccount_Id(dto.articleId(), 1L);
        assertThat(comment.get().getContent()).isEqualTo(dto.content());
    }

    @DisplayName("댓글 아이디를 통해 댓글을 삭제한다 - 성공")
    @Test
    void deleteComment_success() {
        //Given
        Optional<Comment> comment = Optional.of(TestObjectFactory.createComment());
        given(commentRepository.findByIdAndUserAccount_Id(anyLong(), anyLong()))
                .willReturn(comment);
        commentService.deleteComment(1L, 1L);
        //Then
        then(commentRepository).should().findByIdAndUserAccount_Id(1L, 1L);
        assertThat(comment.get().isRemoved()).isEqualTo(true);
    }
}
