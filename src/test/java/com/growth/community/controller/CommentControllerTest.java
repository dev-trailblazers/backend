package com.growth.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

    private CommentDto fixedCommentDto = CommentDto.of(1L, "댓글 내용");

    public CommentControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 댓글 저장 API - 정상 호출")
    @Test
    void saveComment_Success_201() throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(fixedCommentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("[PUT] 댓글 수정 API - 정상 호출")
    @Test
    void updateComment_Success_200() throws Exception {
        mvc.perform(put("/comments")
                        .content(mapper.writeValueAsString(fixedCommentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("[DELETE] 댓글 삭제 API - 정상 호출")
    @Test
    void deleteComment_Success_200() throws Exception {
        mvc.perform(delete("/comments/id/" + 1))
                .andExpect(status().isOk())
                .andDo(print());
    }
}