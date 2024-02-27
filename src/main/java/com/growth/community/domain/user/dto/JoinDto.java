package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.*;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.Regex;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


@Builder
public record JoinDto(
        @Pattern(regexp = Regex.USERNAME, message = ValidationMessage.INVALID_USERNAME_FORMAT)
        String username,
        @Pattern(regexp = Regex.PASSWORD, message = ValidationMessage.INVALID_PASSWORD_FORMAT)
        String password,
        @Pattern(regexp = Regex.PHONE_NUMBER, message = ValidationMessage.INVALID_PHONE_NUMBER_FORMAT)
        String phoneNumber,

        @Pattern(regexp = Regex.NAME, message = ValidationMessage.INVALID_NAME_FORMAT)
        String name,
        Date birth,
        boolean gender,

        @ByteLength(min = 1, max = 30, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        Level level,
        Region workingArea,
        Position position) {

    public static UserAccount toEntity(JoinDto dto) {
        return UserAccount.builder()
                .username(dto.username)
                .password(dto.password)
                .phoneNumber(dto.phoneNumber)
                .name(dto.name)
                .birth(dto.birth)
                .gender(dto.gender)
                .nickname(dto.nickname)
                .level(dto.level)
                .workingArea(dto.workingArea)
                .position(dto.position)
                .role(RoleType.USER)
                .build();
    }
}