package com.hhplus.ddd.controller;

import com.hhplus.ddd.application.LectureApplyApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lecture")
public class LectureController {
    private final LectureApplyApplication lectureApplyApplication;

    @PostMapping("/{lectureId}/users/{userId}")
    public Object applyLecture(
            @PathVariable("userId") Long userId,
            @PathVariable("lectureId") Long lectureId
    ){
        return lectureApplyApplication.applyLecture(userId, lectureId);
    }

    @GetMapping
    public Object getLectures(@RequestParam("date") LocalDate date){
        return lectureApplyApplication.getLecturesByDate(date);
    }

    @GetMapping("/completion/users/{userId}")
    public Object getCompleted(
            @PathVariable("userId") Long userId
    ){
        return lectureApplyApplication.getCompletedByUserId(userId);
    }
}
