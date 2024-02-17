package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.UserAccount;
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

        @Email(message = "이메일 형식이 아닙니다.")
        String email,

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,15}$")
        String password,
        Principal.RoleType role,

        @Length(min = 2, max = 20)
        String name,
        @Length(min = 1, max = 16)      //varchar = 16 * 3 = 48
        String nickname,
        Date birth,
        @Pattern(regexp = "[mf]", message = "성별은 m 또는 f로만 구분할 수 있습니다.")
        char gender,
        @Pattern(regexp = "^[0-9]{11}", message = "전화번호는 11자리 입니다.")
        String phoneNumber,

        String region,
        @Min(0) @Max(40)
        int career,
        String position
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