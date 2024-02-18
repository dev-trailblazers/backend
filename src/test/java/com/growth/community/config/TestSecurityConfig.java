package com.growth.community.config;


import com.growth.community.domain.user.Position;
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
        given(userAccountRepository.findByEmail(anyString())).willReturn(
                Optional.of(new UserAccount(
                        1L,
                        "testuser1@example.com",
                        "password",
                        Principal.RoleType.USER,
                        "CHAN_YEONG",
                        "CHANY",
                        new Date(),
                        'M',
                        "01023953042",
                        "영주",
                        1,
                        Position.WEB_BACKEND,
                        false
                ))
        );
    }
}
