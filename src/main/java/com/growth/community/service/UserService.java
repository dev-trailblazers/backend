package com.growth.community.service;

import com.growth.community.Exception.AlreadyExistsException;
import com.growth.community.Exception.ExceptionMessage;
import com.growth.community.domain.user.UserAccount;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserAccountRepository userAccountRepository;


    public void join(UserAccountDto dto) {
        Optional<UserAccount> duplicatedEmailUser = userAccountRepository.findByEmail(dto.email());
        Optional<UserAccount> duplicatedNicknameUser = userAccountRepository.findByNickname(dto.nickname());

        if(duplicatedEmailUser.isPresent())
            throw new AlreadyExistsException(dto.email(), ExceptionMessage.EMAIL_IS_EXISTING);
        if(duplicatedNicknameUser.isPresent())
            throw new AlreadyExistsException(dto.nickname(), ExceptionMessage.NICKNAME_IS_EXISTING);

        userAccountRepository.save(UserAccountDto.toEntity(dto));
    }
}
