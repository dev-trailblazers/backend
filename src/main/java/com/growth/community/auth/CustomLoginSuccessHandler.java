package com.growth.community.auth;

import com.growth.community.domain.user.dto.Principal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String nickname = getLoginUserNickname(authentication);
            Cookie cookie = new Cookie("USERINFO", nickname);
            response.addCookie(cookie);
    }

    private String getLoginUserNickname(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            Principal principal = (Principal) authentication.getPrincipal();
            return principal.getNickname();
        }
        return null;
    }
}
