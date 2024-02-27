package com.growth.community.dto;

import com.growth.community.domain.user.dto.JoinDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintsValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @DisplayName("아이디가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"aaa", "111", "a1", "abcdefgh123456789"})
    @ParameterizedTest
    void userAccount_invalidUsername_Exception(String username) {
        //Given
        JoinDto dto = JoinDto.builder()
                .username(username)
                .build();
        //When
        Set<ConstraintViolation<JoinDto>> validEmailViolations = validator.validateProperty(dto, "username");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("비밀번호가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"123adsADS", "123asd!@#", "123ASD!@#", "asdASD!@#", "1aS!", "1234asdfASDF!@#$%"})
    @ParameterizedTest
    void userAccount_invalidPassword_Exception(String password) {
        //Given
        JoinDto dto = JoinDto.builder()
                .password(password)
                .build();
        //When
        Set<ConstraintViolation<JoinDto>> validEmailViolations = validator.validateProperty(dto, "password");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("닉네임이 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "가가가가가가가가가가가"})
    @ParameterizedTest
    void userAccount_invalidNickname_Exception(String nickname) {
        //Given
        JoinDto dto = JoinDto.builder()
                .nickname(nickname)
                .build();
        //When
        Set<ConstraintViolation<JoinDto>> validEmailViolations = validator.validateProperty(dto, "nickname");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("전화번호가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"010-1234-1234", "0101234", "0101234567890"})
    @ParameterizedTest
    void userAccount_invalidPhoneNumber_Exception(String phoneNumber) {
        //Given
        JoinDto dto = JoinDto.builder()
                .phoneNumber(phoneNumber)
                .build();
        //When
        Set<ConstraintViolation<JoinDto>> validEmailViolations = validator.validateProperty(dto, "phoneNumber");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }
}
