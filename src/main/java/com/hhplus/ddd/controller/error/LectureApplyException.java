package com.hhplus.ddd.controller.error;

import lombok.Getter;

@Getter
public class LectureApplyException extends RuntimeException {
    private final String message;
    private final ErrorCode errorCode;

    public LectureApplyException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        message = errorCode.getMessage();
        this.errorCode = errorCode;
    }
}