package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.enums.Level;
import com.growth.community.domain.user.enums.Position;
import com.growth.community.domain.user.enums.Region;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;


public record UserUpdateDto(
        @ByteLength(min = 1, max = 30, message = ValidationMessage.NICKNAME_LENGTH)
        String nickname,
        Level level,
        Region workingArea,
        Position position
) {
}
