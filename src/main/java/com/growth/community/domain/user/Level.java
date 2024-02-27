package com.growth.community.domain.user;

public enum Level {
    JOB_SEEKER("취준생"),
    ROOKIE("신입"),
    JUNIOR("1~3년"),
    MIDDLE("4~6년"),
    SENIOR("7년차 이상");

    private final String standard;

    Level(String standard) {
        this.standard = standard;
    }
}
