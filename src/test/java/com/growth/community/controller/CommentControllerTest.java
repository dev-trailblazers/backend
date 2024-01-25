package com.growth.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API - 댓글 컨트롤러")
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
    @Test
    void postComment_dataIsValid_201() throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(createCommentDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("[POST] 댓글 저장 API - 실패(데이터 누락)")
    @MethodSource("invalidCommentDto")
    @ParameterizedTest(name = "{index}: {1}")
    void postComment_missingData_400(CommentDto dto, String message) throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @DisplayName("[PUT] 댓글 수정 API - 정상 호출")
    @Test
    void updateComment_dataIsValid_200() throws Exception {
        mvc.perform(put("/comments")
                        .content(mapper.writeValueAsString(createCommentDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[PUT] 댓글 수정 API - 실패(데이터 누락)")
    @MethodSource("invalidCommentDto")
    @ParameterizedTest(name = "{index}: {1}")
    void updateComment_dataIsInvalid_400(CommentDto dto, String message) throws Exception {
        mvc.perform(put("/comments")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @DisplayName("[DELETE] 댓글 삭제 API - 정상 호출")
    @Test
    void deleteComment_idIsValid_200() throws Exception {
        mvc.perform(delete("/comments/id/" + 1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    static Stream<Arguments> invalidCommentDto(){
        return Stream.of(
                Arguments.of(CommentDto.of(null, "댓글"), "게시글 아이디 누락"),
                Arguments.of(CommentDto.of(1L, null), "댓글 내용 누락"),
                Arguments.of(CommentDto.of(1L, ""), "빈 댓글")
        );
    }

    private CommentDto createCommentDto(){
        return CommentDto.of(1L, "댓글 내용");
    }
}
