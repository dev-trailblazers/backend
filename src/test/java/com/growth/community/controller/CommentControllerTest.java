package com.growth.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.config.TestSecurityConfig;
import com.growth.community.domain.comment.dto.RequestCommentDto;
import com.growth.community.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API - 댓글 컨트롤러")
@Import(TestSecurityConfig.class)
@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    public CommentControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 댓글 저장 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void postComment_dataIsValid_201() throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(createRequestCommentDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("[POST] 댓글 저장 API - 실패(데이터 누락)")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @MethodSource("invalidRequestCommentDto")
    @ParameterizedTest(name = "{index}: {1}")
    void postComment_missingData_400(RequestCommentDto dto, String message) throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @DisplayName("[PUT] 댓글 수정 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void putComment_dataIsValid_200() throws Exception {
        mvc.perform(put("/comments")
                        .content(mapper.writeValueAsString(createRequestCommentDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[PUT] 댓글 수정 API - 실패(데이터 누락)")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @MethodSource("invalidRequestCommentDto")
    @ParameterizedTest(name = "{index}: {1}")
    void putComment_dataIsInvalid_400(RequestCommentDto dto, String message) throws Exception {
        mvc.perform(put("/comments")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @DisplayName("[DELETE] 댓글 삭제 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void deleteComment_idIsValid_200() throws Exception {
        mvc.perform(delete("/comments/id/" + 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    static Stream<Arguments> invalidRequestCommentDto(){
        return Stream.of(
                Arguments.of(RequestCommentDto.of(null, null,"댓글"), "게시글 아이디 누락"),
                Arguments.of(RequestCommentDto.of(1L, null, null), "댓글 내용 누락"),
                Arguments.of(RequestCommentDto.of(1L, null, ""), "빈 댓글")
        );
    }

    private RequestCommentDto createRequestCommentDto(){
        return RequestCommentDto.of(1L, null, "댓글 내용");
    }
}
