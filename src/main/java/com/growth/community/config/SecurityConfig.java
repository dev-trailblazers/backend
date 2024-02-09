package com.growth.community.config;

import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.user.dto.UserAccountDto;
import com.growth.community.repository.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                HttpMethod.GET,
                                "/articles/**",
                                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**"
                                , "/join"
                        ).permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                ).formLogin(login -> login.defaultSuccessUrl("/articles/keyword/world"))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return email -> userAccountRepository.findByEmail(email)
                .map(UserAccountDto::fromEntity)
                .map(Principal::fromDto)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - email:" + email));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3300");
            }
        };
    }

}