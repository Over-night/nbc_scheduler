package com.schedule_manager.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReviseResponseDto {
    private Long id;
    private UUID memberId;
    private Long scheduleId;
    private String content;
    private LocalDateTime updatedAt;
}
