package com.growth.community.controller;

import com.growth.community.domain.auth.SMSRequestDto;
import com.growth.community.domain.user.dto.JoinDto;
import com.growth.community.service.AuthenticationService;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/join")
    public ResponseEntity<Void> joinUser(@RequestBody @Valid JoinDto dto){
        boolean smsAuthenticationStatus = authenticationService.getSMSAuthenticationStatus(dto.phoneNumber());
        userService.join(dto, smsAuthenticationStatus);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //String으로 받을 땐 JSON이 아닌 TEXT 타입으로 전송
    @PostMapping("/username")
    public ResponseEntity<Boolean> checkDuplicateUsername(@RequestBody String username){
        return ResponseEntity.ok().body(userService.checkDuplicateUsername(username));
    }

    @PostMapping("/nickname")
    public ResponseEntity<Boolean> checkDuplicateNickname(@RequestBody String nickname){
        return ResponseEntity.ok().body(userService.checkDuplicateNickname(nickname));
    }

    @PostMapping("/sms")
    public ResponseEntity<Void> sendSMS(@RequestBody @Pattern(regexp = "^[0-9]{11}") String phoneNumber){
        authenticationService.generateSMSAuthenticationCode(phoneNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms-code")
    public ResponseEntity<Void> matchSMSAuthenticationCode(@RequestBody @Valid SMSRequestDto dto){
        authenticationService.matchSMSAuthenticationCode(dto);
        return ResponseEntity.ok().build();
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<String> handleIllegalArgumentOrStateException(RuntimeException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
