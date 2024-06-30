package com.ssafy.exception.plan;

public class PlanNotFoundException extends RuntimeException {
    private static final String MESSAGE = "존재하지 않는 계획입니다.";

    public PlanNotFoundException() {
        super(MESSAGE);
    }
}
