package com.growth.community.domain.validation;

public class ValidationMessage {
    public static final String KEYWORD_LENGTH =  "검색 키워드는 1~30자 사이로 입력해주세요.";
    public static final String HASHTAGS_LENGTH = "해시 태그는 최대 6개 까지만 입력할 수 있습니다.";
    public static final String NICKNAME_LENGTH =  "닉네임은 1~16자 사이로 입력해주세요.";
    public static final String TITLE_IS_REQUIRED = "제목은 필수입니다.";
    public static final String CONTENT_IS_REQUIRED = "내용은 필수입니다.";
    public static final String ARTICLE_ID_IS_REQUIRED = "게시글 아이디는 필수입니다.";

    public static final String INVALID_USERNAME_FORMAT = "사용자 아이디 형식은 영소문자, 숫자를 하나 이상 포함한 3~16 자리로 구성되어야 합니다.";
    public static final String INVALID_PASSWORD_FORMAT = "비밀번호는 대소문자, 숫자, 특수 문자를 각각 하나 이상 포함한 8~16 자리로 구성되어야 합니다.";
    public static final String INVALID_PHONE_NUMBER_FORMAT = "전화번호는 11자리 숫자입니다.";
}
