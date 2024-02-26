package com.growth.community.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.config.TestSecurityConfig;
import com.growth.community.domain.article.dto.ArticleDto;
import com.growth.community.domain.article.dto.RequestArticleDto;
import com.growth.community.service.ArticleService;
import com.growth.community.util.TestObjectFactory;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API - 게시글 컨트롤러")
@Import(TestSecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    private ArticleService articleService;


    public ArticleControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 게시글 생성 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void postArticle_201() throws Exception {
        //Given
        given(articleService.createArticle(any(RequestArticleDto.class), anyLong()))
                .willReturn(TestObjectFactory.createArticle());
        //When & Then
        mvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestObjectFactory.createRequestArticleDto()))
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @DisplayName("[POST] 게시글 생성 API - 실패(잘못된 요청 데이터)")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @MethodSource("invalidArticleDto")
    @ParameterizedTest(name = "{index}: {1}")
    void postArticle_dataIsInvalid_400(ArticleDto dto, String message) throws Exception {
        mvc.perform(post("/articles")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[GET] 게시글 키워드 검색 API - 정상 호출")
    @Test
    void getArticlesByKeyword_200() throws Exception {
        mvc.perform(get("/articles/keyword/" + "keyword"))
                .andExpect(status().isOk());
    }

    @DisplayName("[GET] 게시글 상세 조회 API - 정상 호출")
    @Test
    void getArticleById_200() throws Exception {
        mvc.perform(get("/articles/id/" + 1))
                .andExpect(status().isOk());
    }

    @DisplayName("[PUT] 게시글 수정 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void putArticle_200() throws Exception {
        mvc.perform(put("/articles")
                        .content(mapper.writeValueAsString(TestObjectFactory.createArticleDto()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("[PUT] 게시글 수정 API - 실패(잘못된 요청 데이터)")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @MethodSource("invalidArticleDto")
    @ParameterizedTest(name = "{index}: {1}")
    void putArticle_articleIsInValid_400(ArticleDto dto, String message) throws Exception {
        mvc.perform(put("/articles")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("[DELETE] 게시글 삭제 API - 정상 호출")
    @WithUserDetails(value = "testuser1@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    void deleteArticle_200() throws Exception {
        mvc.perform(delete("/articles/id/" + 1L))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> invalidArticleDto() {
        return Stream.of(
                Arguments.of(ArticleDto.of("제목", "내용", "#1#2#3#4#5#6#7"), "해시태그 개수 초과"),
                Arguments.of(ArticleDto.of(null, "내용", "#해시태그"), "제목 누락"),
                Arguments.of(ArticleDto.of("제목", null, "#해시태그"), "내용 누락")
        );
    }
}