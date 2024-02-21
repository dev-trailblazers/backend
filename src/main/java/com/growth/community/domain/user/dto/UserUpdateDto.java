package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.Position;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;


public record UserUpdateDto(
        @Length(min = 1, max = 16, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        String region,
        @Min(0) @Max(40) int career,
        Position position
) {
}
