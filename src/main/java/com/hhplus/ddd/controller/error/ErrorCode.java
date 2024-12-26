package com.hhplus.ddd.controller.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND_USER(400, "존재하지 않는 사용자입니다."),
    NOT_FOUND_LECTURE(400, "존재하지 않는 강의입니다."),
    NOT_FOUND_LECTURE_LIST(204, "해당 날짜에 강의가 존재하지 않습니다.");

    private int status;
    private String message;
}
