package com.schedule_manager.controller;

import com.schedule_manager.dto.comment.CommentResponseDto;
import com.schedule_manager.dto.comment.CommentUploadRequestDto;
import com.schedule_manager.dto.comment.CommentUploadResponseDto;
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
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{schedule_id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommentUploadResponseDto> uploadComment(@PathVariable Long schedule_id,
                                                                  @RequestBody CommentUploadRequestDto dto,
                                                                  @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        CommentUploadResponseDto response = commentService.uploadComment(schedule_id, username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<CommentResponseDto> findCommentById(@PathVariable Long id) {
        CommentResponseDto foundedCommentById = commentService.findById(id);
        return ResponseEntity.ok(foundedCommentById);
    }

    @GetMapping("/search/member/{username}")
    public ResponseEntity<List<CommentResponseDto>> findCommentByUsername(@PathVariable String username) {
        List<CommentResponseDto> foundedCommentsByUsername = commentService.findByUsername(username);
        return ResponseEntity.ok(foundedCommentsByUsername);
    }

    @GetMapping("/search/schedule/{schedule_id}")
    public ResponseEntity<List<CommentResponseDto>> findCommentByScheduleId(@PathVariable Long schedule_id) {
        List<CommentResponseDto> foundedCommentsByUsername = commentService.findByScheduleId(schedule_id);
        return ResponseEntity.ok(foundedCommentsByUsername);
    }

    @GetMapping("/search-all/member/{username}")
    public ResponseEntity<List<CommentResponseDto>> findCommentByUsernameIncludingDeleted(@PathVariable String username) {
        List<CommentResponseDto> foundedCommentsByUsername = commentService.findByUsernameIncludingDeleted(username);
        return ResponseEntity.ok(foundedCommentsByUsername);
    }

    @GetMapping("/search-all/schedule/{schedule_id}")
    public ResponseEntity<List<CommentResponseDto>> findCommentByScheduleIdIncludingDeleted(@PathVariable Long schedule_id) {
        List<CommentResponseDto> foundedCommentsByUsername = commentService.findByScheduleIdIncludingDeleted(schedule_id);
        return ResponseEntity.ok(foundedCommentsByUsername);
    }
}
