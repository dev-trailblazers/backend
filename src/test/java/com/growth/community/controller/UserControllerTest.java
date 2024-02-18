package com.growth.community.controller;

import com.growth.community.config.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;

@DisplayName("API - 유저 컨트롤러")
@Import(TestSecurityConfig.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {


//    @Test
//    void join_dataIsValid_201() {
//        // TODO: 이메일, 비밀번호, 성별, 전화번호, 경력 검증
//    }
}
