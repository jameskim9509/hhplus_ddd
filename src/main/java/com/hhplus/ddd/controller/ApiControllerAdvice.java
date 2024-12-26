package com.hhplus.ddd.controller;

import com.hhplus.ddd.controller.error.ErrorResponse;
import com.hhplus.ddd.controller.error.LectureApplyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiControllerAdvice {
    @ExceptionHandler(LectureApplyException.class)
    public ResponseEntity<ErrorResponse> handleLectureException(LectureApplyException e)
    {
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(
                        new ErrorResponse(e.getErrorCode(), e.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex)
    {
        return ResponseEntity
                .status(500)
                .body(ex.getMessage());
    }
}