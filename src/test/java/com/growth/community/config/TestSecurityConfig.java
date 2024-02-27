package com.growth.community.config;


import com.growth.community.domain.user.*;
import com.growth.community.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {
    @MockBean
    private UserAccountRepository userAccountRepository;

    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findByUsername(anyString()))
                .willReturn(Optional.of(UserAccount.builder()
                        .id(1L)
                        .username("tester1")
                        .password("asdQWE123!@#")
                        .phoneNumber("01012345678")
                        .role(RoleType.USER)
                        .name("테스터")
                        .birth(new Date())
                        .gender(true)
                        .nickname("T_tester")
                        .level(Level.ROOKIE)
                        .workingArea(Region.대구)
                        .position(Position.WEB_BACKEND)
                        .role(RoleType.USER)
                        .isDeactivated(false)
                        .build()
                )
        );
    }
}
