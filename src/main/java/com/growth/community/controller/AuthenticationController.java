package com.growth.community.controller;

import com.growth.community.domain.user.dto.JoinDto;
import com.growth.community.service.SMSService;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final SMSService SMSService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid JoinDto dto){
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> SMSAuthentication(@RequestBody String phoneNumber){
        //String으로 받을 땐 JSON이 아닌 TEXT 타입으로 전송
        SMSService.sendSMS(phoneNumber);
        return ResponseEntity.ok().build();
    }

}
