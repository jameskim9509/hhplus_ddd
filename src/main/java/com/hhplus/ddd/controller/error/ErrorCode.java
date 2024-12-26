package com.hhplus.ddd.controller.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_APPLIED(400, "이미 신청한 강의입니다."),
    PAST_LECTURE_APPLIED(400, "과거의 강의는 신청할 수 없습니다."),
    APPLICANT_COUNT_EXCEEDED(400, "수강 가능 인원을 초과하였습니다."),
    NOT_FOUND_USER(400, "존재하지 않는 사용자입니다."),
    NOT_FOUND_LECTURE(400, "존재하지 않는 강의입니다."),
    NOT_FOUND_LECTURE_LIST(204, "해당 날짜에 강의가 존재하지 않습니다."),
    NOT_FOUND_APPLIED(204, "신청한 강의 목록이 없습니다.");

    private int status;
    private String message;
}
