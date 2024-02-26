package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.Position;
import com.growth.community.domain.user.Region;
import com.growth.community.domain.user.RoleType;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.validation.Regex;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


@Builder
public record UserAccountDto(
        Long id,

        @Pattern(regexp = Regex.USERNAME, message = ValidationMessage.INVALID_USERNAME_FORMAT)
        String username,

        @Pattern(regexp = Regex.PASSWORD, message = ValidationMessage.INVALID_PASSWORD_FORMAT)
        String password,
        RoleType role,
        String name,
        @Length(min = 1, max = 16, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        Date birth,
        boolean gender,
        @Pattern(regexp = Regex.PHONE_NUMBER, message = ValidationMessage.INVALID_PHONE_NUMBER_FORMAT)
        String phoneNumber, //todo: 본인 인증을 통한 인증

        @Min(0) @Max(40) byte career,
        Position position,
        Region workingArea) {

    public static UserAccountDto of(Long id, String username, String password) {
        return UserAccountDto.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
    }

    public static UserAccountDto fromEntity(UserAccount userAccount) {
        return UserAccountDto.builder()
                .id(userAccount.getId())
                .username(userAccount.getUsername())
                .password(userAccount.getPassword())
                .role(userAccount.getRole())
                .name(userAccount.getName())
                .nickname(userAccount.getNickname())
                .birth(userAccount.getBirth())
                .gender(userAccount.isGender())
                .phoneNumber(userAccount.getPhoneNumber())
                .workingArea(userAccount.getWorkingArea())
                .career(userAccount.getCareer())
                .position(userAccount.getPosition())
                .build();
    }

    public static UserAccount toEntity(UserAccountDto dto) {
        return UserAccount.builder()
                .id(dto.id)
                .username(dto.username)
                .password(dto.password)
                .role(dto.role)
                .name(dto.name)
                .nickname(dto.nickname)
                .birth(dto.birth)
                .gender(dto.gender)
                .phoneNumber(dto.phoneNumber)
                .workingArea(dto.workingArea)
                .career(dto.career)
                .position(dto.position)
                .build();
    }
}