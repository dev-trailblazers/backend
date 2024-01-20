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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @DisplayName("[POST] 댓글 저장 API - 정상 호출 ")
    @Test
    void saveComment_Success_201() throws Exception {
        mvc.perform(post("/comments")
                        .content(mapper.writeValueAsString(fixedCommentDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
