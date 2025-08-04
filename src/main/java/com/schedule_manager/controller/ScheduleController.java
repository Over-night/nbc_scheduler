package com.schedule_manager.controller;

import com.schedule_manager.dto.comment.*;
import com.schedule_manager.dto.schedule.*;
import com.schedule_manager.service.CommentService;
import com.schedule_manager.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
@Slf4j
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<ScheduleResponseDto> allSchedule = scheduleService.findAll();
        return ResponseEntity.ok(allSchedule);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ScheduleResponseDto> uploadSchedule(@RequestBody ScheduleUploadRequestDto dto,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        ScheduleResponseDto response = scheduleService.uploadSchedule(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleWithCommentResponseDto> getScheduleById(@PathVariable Long id) {
        ScheduleWithCommentResponseDto foundScheduleById = scheduleService.findById(id);
        return ResponseEntity.ok(foundScheduleById);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentUploadResponseDto> uploadComment(@PathVariable Long id,
                                                                    @RequestBody CommentUploadRequestDto dto,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        CommentUploadResponseDto response = commentService.uploadComment(id, username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ScheduleReviseResponseDto> uploadSchedule(@PathVariable Long id,
                                                                    @RequestBody ScheduleReviseRequestDto dto,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        ScheduleReviseResponseDto response = scheduleService.reviseSchedule(id, username, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ScheduleDeleteResponseDto> deleteSchedule(@PathVariable Long id,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        ScheduleDeleteResponseDto response = scheduleService.deleteSchedule(id, username);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}/comments/{comment_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentReviseResponseDto> reviseComment(@PathVariable Long id,
                                                                  @PathVariable Long comment_id,
                                                                    @RequestBody CommentReviseRequestDto dto,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        CommentReviseResponseDto response = commentService.reviseComment(id, comment_id, username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @DeleteMapping("/{id}/comments/{comment_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentDeleteResponseDto> deleteComment(@PathVariable Long id,
                                                                  @PathVariable Long comment_id,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        CommentDeleteResponseDto response = commentService.deleteComment(id, comment_id, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/member/{username}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByUsername(@PathVariable String username) {
        List<ScheduleResponseDto> foundScheduleByUsername = scheduleService.findByUsername(username);
        return ResponseEntity.ok(foundScheduleByUsername);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedulesIncludingDeleted() {
        List<ScheduleResponseDto> allSchedule = scheduleService.findAllIncludingDeleted();
        return ResponseEntity.ok(allSchedule);
    }

    @GetMapping("/all/member/{username}")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByUsernameIncludingDeleted(@PathVariable String username) {
        List<ScheduleResponseDto> foundScheduleByUsername = scheduleService.findByUsernameIncludingDeleted(username);
        return ResponseEntity.ok(foundScheduleByUsername);
    }
}
