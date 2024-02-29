package com.growth.community.controller;

import com.growth.community.domain.user.SMSAuthenticationInfo;
import com.growth.community.domain.user.dto.JoinDto;
import com.growth.community.service.AuthenticationService;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid JoinDto dto){
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> smsAuthentication(@RequestBody @Pattern(regexp = "^[0-9]{11}") String phoneNumber){
        //String으로 받을 땐 JSON이 아닌 TEXT 타입으로 전송
        authenticationService.phoneNumberAuthenticationCode(phoneNumber);
        return ResponseEntity.ok().build();
    }
    
}
