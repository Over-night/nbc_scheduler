package com.schedule_manager.controller;

import com.schedule_manager.dto.schedule.ScheduleResponseDto;
import com.schedule_manager.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> allSchedule = scheduleService.findAll();
        return ResponseEntity.ok(allSchedule);
    }

//    @PostMapping("/schedule")
//    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleUploadRequestDto requestDto,
//                                                              @AuthenticationPrincipal ) {
//        List<ScheduleResponseDto> allSchedule = scheduleService.findAll();
//        return ResponseEntity.ok(allSchedule);
//    }
//
//    @PostMapping
//    public ResponseEntity<PostResponseDto> createPost(@RequestBody @Valid PostCreateRequestDto requestDto,
//                                                      @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        UUID userId = userPrincipal.getId(); // 로그인된 사용자 ID
//        PostResponseDto responseDto = postService.createPost(requestDto, userId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
//    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDto foundScheduleById = scheduleService.findById(id);
        return ResponseEntity.ok(foundScheduleById);
    }

    @GetMapping("/schedule/user/{username}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByUsername(@PathVariable String username) {
        List<ScheduleResponseDto> foundScheduleByUsername = scheduleService.findByUsername(username);
        return ResponseEntity.ok(foundScheduleByUsername);
    }
}
