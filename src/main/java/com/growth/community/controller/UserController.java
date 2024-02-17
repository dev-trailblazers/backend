package com.growth.community.controller;

import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String principalHealthCheck(@AuthenticationPrincipal Principal principal){
        return principal.toString();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid UserAccountDto dto){
        userService.join(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
