package com.growth.community.controller;

import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> join(@RequestBody UserAccountDto dto){
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
