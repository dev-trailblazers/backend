package com.growth.community.domain.validation;

public class Regex {
    public static final String USERNAME = "^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{3,16}$";
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$";
    public static final String PHONE_NUMBER = "^[0-9]{11}";

    public static final String NAME = "^[가-힣]{2,6}$";
}
