package com.growth.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.config.TestSecurityConfig;
import com.growth.community.service.AuthenticationService;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("API - 인증 컨트롤러")
@Import(TestSecurityConfig.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean private UserService userService;
    @MockBean private AuthenticationService authenticationService;


    public AuthenticationControllerTest(@Autowired MockMvc mvc, @Autowired ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("[POST] 회원 가입 API - 정상 호출")
    @Test
    void join_201() throws Exception {
        given(authenticationService.getSMSAuthenticationStatus(anyString())).willReturn(true);
        mvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestObjectFactory.createJoinDto()))
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
