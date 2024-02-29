package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.*;
import com.growth.community.domain.user.enums.Level;
import com.growth.community.domain.user.enums.Position;
import com.growth.community.domain.user.enums.Region;
import com.growth.community.domain.user.enums.RoleType;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.Date;


@Builder
public record JoinDto(
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{3,16}$", message = ValidationMessage.INVALID_USERNAME_FORMAT)
        String username,    //영어 소문자 + 숫자 조합으로 3~16자
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$", message = ValidationMessage.INVALID_PASSWORD_FORMAT)
        String password,    //영대소문자 + 특수문자 + 숫자가 1개 이상 총 8~16자
        @Pattern(regexp = "^[0-9]{11}", message = ValidationMessage.INVALID_PHONE_NUMBER_FORMAT)
        String phoneNumber, //11자리 숫자

        @Pattern(regexp = "^[가-힣]{2,6}$", message = ValidationMessage.INVALID_NAME_FORMAT)
        String name,        //한글 2~6자
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