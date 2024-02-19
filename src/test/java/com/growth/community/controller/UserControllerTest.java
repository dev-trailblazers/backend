package com.growth.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.config.TestSecurityConfig;
import com.growth.community.service.UserService;
import com.growth.community.util.TestObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API - 유저 컨트롤러")
@Import(TestSecurityConfig.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean private UserService userService;


    public UserControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 회원 가입 API - 정상 호출")
    @Test
    void join_201() throws Exception {
        mvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestObjectFactory.createUserAccountDto()))
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
