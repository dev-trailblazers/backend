package com.growth.community.service;

import com.growth.community.domain.auth.SMSAuthenticationInfo;
import com.growth.community.domain.auth.SMSRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final RedisTemplate<String, SMSAuthenticationInfo> redisTemplate;
    private final SMSService smsService;
    private final String prefix = "authentication: ";

    public void generateSMSAuthenticationCode(String phoneNumber) {
        String authenticationCode = saveAuthenticationCode(phoneNumber);
//        smsService.sendSMS(phoneNumber, authenticationCode);
    }

    //todo: 반복적으로 요청할 확률이 높기 때문에 캐싱을 고려해보자.
    public void matchSMSAuthenticationCode(SMSRequestDto dto){
        String key = prefix + dto.phoneNumber();
        SMSAuthenticationInfo smsAuthenticationInfo = Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 전화번호 입니다."));

        smsAuthenticationInfo.match(dto.authenticationCode());
        redisTemplate.opsForValue().set(key, smsAuthenticationInfo, 12, TimeUnit.HOURS);
    }

    public boolean getSMSAuthenticationStatus(String phoneNumber){
        String key = prefix + phoneNumber;
        SMSAuthenticationInfo smsAuthenticationInfo = Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElseThrow(() -> new NoSuchElementException("유효하지 않은 전화번호 입니다."));

        return smsAuthenticationInfo.isAuthenticationStatus();
    }

    private String saveAuthenticationCode(String phoneNumber){
        String key = prefix + phoneNumber;
        SMSAuthenticationInfo smsAuthenticationInfo = redisTemplate.opsForValue().get(key);

        if(smsAuthenticationInfo == null){
            smsAuthenticationInfo = new SMSAuthenticationInfo();
            redisTemplate.opsForValue().set(key, smsAuthenticationInfo, 1, TimeUnit.DAYS);
        } else {
            smsAuthenticationInfo.refreshAuthenticateCode();
            Long remainingExpireTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(key, smsAuthenticationInfo);
            redisTemplate.expire(key, remainingExpireTime, TimeUnit.SECONDS);
        }
        return smsAuthenticationInfo.getAuthenticationCode();
    }

}
