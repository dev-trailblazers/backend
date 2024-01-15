package com.code.reviewer.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.code.reviewer.domain.article.dto.ArticleDto;
import com.code.reviewer.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Stream;
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

@DisplayName("API - 게시글 컨트롤러")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private ArticleService articleService;

    private ArticleDto fixedArticleDto = ArticleDto.of("제목", "내용", "#해시태그", List.of());


    public ArticleControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 게시글 생성 API - 정상 호출")
    @Test
    void saveArticle_Success_201() throws Exception {
        mvc.perform(post("/articles")
                .content(mapper.writeValueAsString(fixedArticleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("[POST] 게시글 생성 API - 실패")
    @MethodSource("invalidSave")
    @ParameterizedTest(name = "{index}: {1}")
    void saveArticle_Fail_400(ArticleDto articleDto, String message) throws Exception {
        mvc.perform(post("/articles")
                        .content(mapper.writeValueAsString(articleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[GET] 게시글 제목 검색 API - 성공")
    @Test
    void searchArticlesByTitle_Success_200() throws Exception {
        mvc.perform(get("/articles/title/" + "keyword"))
                .andExpect(status().isOk());
    }

    @DisplayName("[GET] 게시글 해시태그 검색 API - 성공")
    @Test
    void searchArticlesByHashTag_Success_200() throws Exception {
        mvc.perform(get("/articles/hashtag/" + "hashtag"))
                .andExpect(status().isOk());
    }

    @DisplayName("[GET] 게시글 상세 조회 API - 성공")
    @Test
    void getArticleById_Success_200() throws Exception {
        mvc.perform(get("/articles/id/" + 1))
                .andExpect(status().isOk());
    }

    @DisplayName("[PUT] 게시글 수정 API - 성공")
    @Test
    void updateArticle_Success_200() throws Exception {
        mvc.perform(put("/articles")
                        .content(mapper.writeValueAsString(fixedArticleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("[DELETE] 게시글 삭제 API - 성공")
    @Test
    void deleteArticle_Success_200() throws Exception {
        mvc.perform(delete("/articles/id/" + 1L))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> invalidSave() {
        return Stream.of(
                Arguments.of(ArticleDto.of("제목", "내용", "#1#2#3#4#5#6#7", List.of()), "해시태그 개수 초과"),
                Arguments.of(ArticleDto.of(null, "내용", "#해시태그", List.of()), "제목 누락"),
                Arguments.of(ArticleDto.of("제목", null, "#해시태그", List.of()), "내용 누락")
        );
    }
}