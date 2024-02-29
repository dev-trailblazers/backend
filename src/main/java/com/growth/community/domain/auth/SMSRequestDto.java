package com.growth.community.domain.auth;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record SMSRequestDto(
        @Pattern(regexp = "^[0-9]{11}") String phoneNumber,
        @Length(min = 6, max = 6) String authenticationCode
) {

}
