package com.growth.community.domain.user.dto;

import com.growth.community.domain.user.RoleType;
import com.growth.community.domain.user.UserAccount;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


public class Principal extends User {
    @Getter
    private final Long id;

    public Principal(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

//    public static Principal of(Long id, String username, String password, RoleType role) {
//        return new Principal(
//                id,
//                username,
//                password,
//                createAuthoritiesFromRoles(Set.of(role))
//        );
//    }

    public static Principal fromEntity(UserAccount userAccount) {
        return new Principal(
                userAccount.getId(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                createAuthoritiesFromRoles(
                        Set.of(userAccount.getRole())
                ));
    }

    private static Set<SimpleGrantedAuthority> createAuthoritiesFromRoles(Set<RoleType> roleTypes) {
        return roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet());
    }
}

