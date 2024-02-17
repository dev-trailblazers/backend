package com.growth.community.controller;

import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RequiredArgsConstructor
@RestController
public class UserController {
    private final AuthenticationManager authenticationManager;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest){
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
        return ResponseEntity.ok().build();
    }

    public record LoginRequest(String email, String password){}
}
