package com.growth.community.domain.user.enums;

import lombok.Getter;

public enum RoleType {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    @Getter
    private final String name;
    RoleType(String name){
        this.name = name;
    }
}