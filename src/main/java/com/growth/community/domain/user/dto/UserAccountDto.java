package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.*;
import com.growth.community.domain.user.enums.Level;
import com.growth.community.domain.user.enums.Position;
import com.growth.community.domain.user.enums.Region;
import lombok.Builder;

import java.util.Date;


@Builder
public record UserAccountDto(
        Long id,

        String username,
        String password,
        String phoneNumber,

        String name,
        Date birth,
        boolean gender,

        String nickname,
        Level level,
        Region workingArea,
        Position position) {

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
                .phoneNumber(userAccount.getPhoneNumber())
                .name(userAccount.getName())
                .birth(userAccount.getBirth())
                .gender(userAccount.isGender())
                .nickname(userAccount.getNickname())
                .level(userAccount.getLevel())
                .workingArea(userAccount.getWorkingArea())
                .position(userAccount.getPosition())
                .build();
    }

    public static UserAccount toEntity(UserAccountDto dto) {
        return UserAccount.builder()
                .id(dto.id)
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
                .build();
    }
}