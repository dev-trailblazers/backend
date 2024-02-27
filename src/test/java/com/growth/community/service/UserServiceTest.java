package com.growth.community.service;

import com.growth.community.Exception.AlreadyExistsException;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.user.dto.JoinDto;
import com.growth.community.repository.UserAccountRepository;
import com.growth.community.util.TestObjectFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@DisplayName("비즈니스 로직 - 유저")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks UserService userService;
    @Mock UserAccountRepository userAccountRepository;

    @DisplayName("회원가입 - 성공")
    @Test
    void join_success() {
        //Given
        JoinDto joinDto = TestObjectFactory.createJoinDto();
        //When
        userService.join(joinDto);
        //Then
        then(userAccountRepository).should().save(any(UserAccount.class));
    }

    @DisplayName("닉네임 중복 시 회원가입에 실패하고 예외를 발생한다.")
    @Test
    void join_duplicateNickname_exception() {
        //Given
        given(userAccountRepository.existsByNickname(anyString())).willReturn(true);
        JoinDto joinDto = TestObjectFactory.createJoinDto();
        //When & Then
        assertThatThrownBy(() -> userService.join(joinDto))
                .isInstanceOf(AlreadyExistsException.class);
    }

    @DisplayName("이메일 중복 시 회원가입에 실패하고 예외를 발생한다.")
    @Test
    void join_duplicateEmail_exception() {
        //Given
        given(userAccountRepository.existsByUsername(anyString())).willReturn(true);
        JoinDto joinDto = TestObjectFactory.createJoinDto();
        //When & Then
        assertThatThrownBy(() -> userService.join(joinDto))
                .isInstanceOf(AlreadyExistsException.class);
    }
}