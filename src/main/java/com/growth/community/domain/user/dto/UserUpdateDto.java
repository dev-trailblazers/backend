package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.Level;
import com.growth.community.domain.user.Position;
import com.growth.community.domain.user.Region;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;


public record UserUpdateDto(
        @ByteLength(min = 1, max = 30, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        Level level,
        Region workingArea,
        Position position
) {
}
