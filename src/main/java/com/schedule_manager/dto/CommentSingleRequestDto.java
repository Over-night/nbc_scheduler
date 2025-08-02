package com.schedule_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSingleRequestDto {
    private UUID userId;
    private Long scheduleId;
    private String content;
}
