package com.ssafy.exception.member;

public class MemberNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 사용자입니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
