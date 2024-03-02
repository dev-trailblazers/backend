package com.growth.community.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.domain.user.dto.Principal;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Map<String, String> userInfo = getLoginUserInfo(authentication);
        Cookie cookie = new Cookie("USERINFO", convertToJson(userInfo));
        response.addCookie(cookie);
    }

    private Map<String, String> getLoginUserInfo(Authentication authentication) {
        HashMap<String, String> userInfo = new HashMap<>();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            Principal principal = (Principal) authentication.getPrincipal();
            userInfo.put("userId", principal.getId().toString());
            userInfo.put("nickname", principal.getNickname());
            return userInfo;
        }
        return null;
    }

    private String convertToJson(Map<String, String> map) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(map);
        return URLEncoder.encode(jsonString, "UTF-8");
    }
}
