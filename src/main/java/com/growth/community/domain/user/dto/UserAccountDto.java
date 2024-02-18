package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.Position;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


@Builder
public record UserAccountDto(
        Long id,

        @Email(message = ValidationMessage.EMAIL_FORMAT)
        String email,   //todo: 본인 인증을 통한 인증

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$", message = ValidationMessage.INVALID_PASSWORD)
        String password,
        Principal.RoleType role,
        String name,
        @Length(min = 1, max = 16, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        Date birth,
        @Pattern(regexp = "[mf]", message = ValidationMessage.INVALID_GENDER)
        char gender,
        @Pattern(regexp = "^[0-9]{11}", message = ValidationMessage.PHONE_NUMBER_FORMAT)
        String phoneNumber, //todo: 본인 인증을 통한 인증

        String region,
        @Min(0) @Max(40)
        int career,
        Position position
) {

        public static UserAccountDto of(Long id, String email, String password, String nickname) {
                return new UserAccountDto(
                        id,
                        email,
                        password,
                        null,
                        null,
                        nickname,
                        null,
                        'F',
                        null,
                        null,
                        1,
                        null
                );
        }

        public static UserAccountDto fromEntity(UserAccount userAccount){
                return UserAccountDto.builder()
                        .id(userAccount.getId())
                        .email(userAccount.getEmail())
                        .password(userAccount.getPassword())
                        .role(userAccount.getRole())
                        .name(userAccount.getName())
                        .nickname(userAccount.getNickname())
                        .birth(userAccount.getBirth())
                        .gender(userAccount.getGender())
                        .phoneNumber(userAccount.getPhoneNumber())
                        .region(userAccount.getRegion())
                        .career(userAccount.getCareer())
                        .position(userAccount.getPosition())
                        .build();
        }

        public static UserAccount toEntity(UserAccountDto dto) {
                return UserAccount.builder()
                        .id(dto.id)
                        .email(dto.email)
                        .password(dto.password)
                        .role(dto.role)
                        .name(dto.name)
                        .nickname(dto.nickname)
                        .birth(dto.birth)
                        .gender(dto.gender)
                        .phoneNumber(dto.phoneNumber)
                        .region(dto.region)
                        .career(dto.career)
                        .position(dto.position)
                        .build();
        }
}