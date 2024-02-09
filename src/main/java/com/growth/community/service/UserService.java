package com.growth.community.service;

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
        Optional<UserAccount> user = userAccountRepository.findByEmail(dto.email());

        if(user.isPresent())
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");

        userAccountRepository.save(UserAccountDto.toEntity(dto));
    }
}
