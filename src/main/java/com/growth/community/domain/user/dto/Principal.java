package com.growth.community.domain.user.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record Principal(
        Long id,
        String email,
        String password,
        String nickname,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {
    public static Principal of(Long userId, String password, String email, String nickname) {
        Set<RoleType> roleTypes = Set.of(RoleType.values());

        return new Principal(
                userId,
                email,
                password,
                nickname,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static Principal fromDto(UserAccountDto dto){
        return Principal.of(
                dto.id(),
                dto.password(),
                dto.email(),
                dto.nickname()
        );
    }

    public static UserAccountDto toDto(Principal principal){
        return UserAccountDto.of(
                principal.id,
                principal.email,
                principal.password,
                principal.nickname
        );
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; }
    public Long getUserId() { return id; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public enum RoleType {
        USER("ROLE_USER");

        @Getter private final String name;
        RoleType(String name){
            this.name = name;
        }
    }
}
