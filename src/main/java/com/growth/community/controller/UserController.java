package com.growth.community.controller;

import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.domain.user.dto.UserUpdateDto;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


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
        return ResponseEntity.created(URI.create("/")).build();
    }

    @PostMapping("/user/info")
    public ResponseEntity<UserAccountDto> getUserInfo(@AuthenticationPrincipal Principal principal){
        UserAccountDto dto = userService.inquiryUser(principal.getUserId());
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/user/update")
    public ResponseEntity<UserAccountDto> putUser(@RequestBody UserUpdateDto dto,
                                                  @AuthenticationPrincipal Principal principal){
        UserAccountDto userAccountDto = userService.updateUser(dto, principal.id());
        return ResponseEntity.ok().body(userAccountDto);
    }

    public record LoginRequest(String email, String password){}
}
