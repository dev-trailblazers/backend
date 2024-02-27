package com.growth.community.service;

import com.growth.community.Exception.AlreadyExistsException;
import com.growth.community.Exception.ExceptionMessage;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.user.dto.JoinDto;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.domain.user.dto.UserUpdateDto;
import com.growth.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    private final UserAccountRepository userAccountRepository;


    public void join(JoinDto dto) {
        if (userAccountRepository.existsByUsername(dto.username()))
            throw new AlreadyExistsException(dto.username(), ExceptionMessage.EMAIL_IS_EXISTING);
        if (userAccountRepository.existsByNickname(dto.nickname()))
            throw new AlreadyExistsException(dto.nickname(), ExceptionMessage.NICKNAME_IS_EXISTING);

        userAccountRepository.save(JoinDto.toEntity(dto));
    }

    public UserAccountDto inquiryUser(Long userId) {
        return userAccountRepository.findById(userId)
                .map(UserAccountDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public UserAccountDto updateUser(UserUpdateDto dto, Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId).orElseThrow();

        if (userAccountRepository.existsByNickname(dto.nickname()))
            throw new AlreadyExistsException(dto.nickname(), ExceptionMessage.NICKNAME_IS_EXISTING);
        
        userAccount.setNickname(dto.nickname());
        userAccount.setWorkingArea(dto.workingArea());
        userAccount.setLevel(dto.level());
        userAccount.setPosition(dto.position());
        return UserAccountDto.fromEntity(userAccount);
    }
}
