package com.growth.community.controller;

import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.domain.user.dto.UserUpdateDto;
import com.growth.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;


    @GetMapping("/info")
    public ResponseEntity<UserAccountDto> getUserInfo(@AuthenticationPrincipal Principal principal){
        UserAccountDto dto = userService.inquiryUser(principal.getId());
        return ResponseEntity.ok().body(dto);
    }

    @PutMapping("/info")
    public ResponseEntity<UserAccountDto> putUser(@RequestBody UserUpdateDto dto,
                                                  @AuthenticationPrincipal Principal principal){
        UserAccountDto userAccountDto = userService.updateUser(dto, principal.getId());
        return ResponseEntity.ok().body(userAccountDto);
    }

}
