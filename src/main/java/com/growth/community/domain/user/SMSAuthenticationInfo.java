package com.growth.community.domain.user;


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

    public SMSAuthenticationInfo(String authenticationCode) {
        this.authenticationCode = authenticationCode;
        this.expirationTime = LocalDateTime.now().plusMinutes(5);
    }
}
