package com.growth.community.config;

import com.growth.community.Exception.ExceptionMessage;
import com.growth.community.auth.CustomLoginSuccessHandler;
import com.growth.community.domain.user.dto.Principal;
import com.growth.community.repository.UserAccountRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers(
                            HttpMethod.GET,
                            "/articles/**",
                            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**"
                    ).permitAll()
                    .requestMatchers(
                            HttpMethod.POST,
                            "/auth/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            );

        http
            .formLogin(login -> login.successHandler(new CustomLoginSuccessHandler()))
            .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler((request, response, authentication) ->
                            response.setStatus(HttpServletResponse.SC_OK)
                    ).invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "USERINFO")
            );

        http
                .sessionManagement(session -> session.invalidSessionUrl("/logout") //세션이 유효하지 않을 시 /logout으로 이동해서 쿠키 삭제
                        .maximumSessions(1)    //동시 접속 1명까지 허용
                        .maxSessionsPreventsLogin(false)    //신규 로그인 시 기존 사용자 세션 종료
                        .expiredUrl("http://localhost:3000")    //세션 만료 시 해당 페이지로 이동
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository.findByUsername(username)
                .map(Principal::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND, username)));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);

        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "FETCH"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}