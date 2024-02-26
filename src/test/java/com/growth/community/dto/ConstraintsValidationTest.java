package com.growth.community.dto;

import com.growth.community.domain.user.dto.UserAccountDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstraintsValidationTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @DisplayName("이메일 정보가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @Test
    void userAccount_invalidEmail_Exception() {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .username("잘못된 이메일")
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "email");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("비밀번호가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"123adsADS", "123asd!@#", "123ASD!@#", "asdASD!@#", "1aS!", "1234asdfASDF!@#$%"})
    @ParameterizedTest
    void userAccount_invalidPassword_Exception(String password) {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .password(password)
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "password");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("비밀번호가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"", "16자 이상의 닉네임 사용 불가"})
    @ParameterizedTest
    void userAccount_invalidNickname_Exception(String nickname) {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .nickname(nickname)
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "nickname");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("성별은 m,f가 아닌 다른 문자가 들어갈 시 예외가 발생한다.")
    @Test
    void userAccount_invalidGender_Exception() {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .gender(true)
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "gender");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("전화번호가 지정된 포맷을 지키지 않는다면 예외가 발생한다.")
    @ValueSource(strings = {"010-1234-1234", "0101234", "0101234567890"})
    @ParameterizedTest
    void userAccount_invalidPhoneNumber_Exception(String phoneNumber) {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .phoneNumber(phoneNumber)
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "phoneNumber");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }

    @DisplayName("경력은 0~40 범위를 넘어가면 예외가 발생한다.")
    @ValueSource(ints = {-1, 41})
    @ParameterizedTest
    void userAccount_invalidCareer_Exception(byte career) {
        //Given
        UserAccountDto dto = UserAccountDto.builder()
                .career(career)
                .build();
        //When
        Set<ConstraintViolation<UserAccountDto>> validEmailViolations = validator.validateProperty(dto, "career");
        //Then
        assertThat(validEmailViolations.size()).isEqualTo(1);
    }
}
