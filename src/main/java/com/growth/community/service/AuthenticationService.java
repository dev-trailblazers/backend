package com.growth.community.service;

import com.growth.community.domain.user.SMSAuthenticationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
    private final RedisTemplate<String, SMSAuthenticationInfo> redisTemplate;
    private final SMSService smsService;

    private final static int AUTHENTICATION_CODE_LENGTH = 6;



    /**
     * 1. 전화번호에 해당하는 인증 번호를 생성
     * 2. 인증 번호를 Redis에 저장
     * 3. 유저에게 인증 번호 문자 발송
     * */
    public void phoneNumberAuthenticationCode(String phoneNumber) {
        String code = generateAuthenticationCode();
        saveAuthenticationCode(phoneNumber, code);
//        smsService.sendSMS(phoneNumber, code);
    }

    private String generateAuthenticationCode(){
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

    private void saveAuthenticationCode(String phoneNumber, String authenticationCode){
        SMSAuthenticationInfo smsAuthenticationInfo = new SMSAuthenticationInfo(authenticationCode);

        String key = "authentication: " + phoneNumber;

        redisTemplate.opsForValue().set(key, smsAuthenticationInfo, 1, TimeUnit.DAYS);
    }
}
