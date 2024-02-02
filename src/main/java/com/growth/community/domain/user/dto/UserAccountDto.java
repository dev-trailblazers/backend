package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.Date;


@Builder
public record UserAccountDto(
        Long id,
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        String password,
        String role,

        String name,
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

        public static UserAccountDto fromEntity(UserAccount userAccount){
                return UserAccountDto.builder()
                        .id(userAccount.getId())
                        .build();
        }

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
}