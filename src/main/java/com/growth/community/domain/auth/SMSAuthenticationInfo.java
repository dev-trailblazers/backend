package com.growth.community.domain.auth;


import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
public class SMSAuthenticationInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String authenticationCode;
    private LocalDateTime expirationTime;
    private boolean authenticationStatus;
    private int requestCount;
    private int inputCount;

    private static final byte MAX_COUNT = 5;

    public SMSAuthenticationInfo() {
        this.expirationTime = LocalDateTime.now().plusMinutes(5);
        generateCode();
    }

    public void match(String code){
        inputCount++;
        validateState();

        if(authenticationCode.equals(code)){
            authenticationStatus = true;
        } else {
            throw new IllegalArgumentException("잘못된 인증번호 입니다.");
        }
    }

    public void refreshAuthenticateCode(){
        if(requestCount >= MAX_COUNT) {
            throw new IllegalStateException("인증번호 발송 요청 횟수가 초과되었습니다.");
        }
        generateCode();
        requestCount++;
    }

    private void validateState(){
        if(inputCount > MAX_COUNT){
            throw new IllegalStateException("인증 요청 횟수가 초과되었습니다.");
        }
        if(expirationTime.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("인증 번호가 만료되었습니다. 다시 발급해주세요.");
        }
        if(authenticationStatus){
            throw new IllegalStateException("이미 인증된 상태입니다.");
        }
    }

    private void generateCode(){
        authenticationCode = String.format("%06d", (int) (Math.random() * 1_000_000));
    }
}
