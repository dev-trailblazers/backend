package com.growth.community.domain.user;

public enum Level {
    ROOKIE("신입"),
    JUNIOR("1~3년 차"),
    MIDDLE("4~6년 차"),
    SENIOR("7년차 이상");

    private final String standard;

    Level(String standard) {
        this.standard = standard;
    }
}
