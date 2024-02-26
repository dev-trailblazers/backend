package com.growth.community.config;


import com.growth.community.domain.user.Position;
import com.growth.community.domain.user.Region;
import com.growth.community.domain.user.RoleType;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.user.dto.Principal;
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
                        .username("testuser1@example.com")
                        .password("password")
                        .role(RoleType.USER)
                        .nickname("Chany")
                        .birth(new Date(1999, 11, 23))
                        .gender(true)
                        .phoneNumber("01012345678")
                        .workingArea(Region.대구)
                        .career((byte) 1)
                        .position(Position.WEB_BACKEND)
                        .isDeactivated(false)
                        .build()
                )
        );
    }
}
