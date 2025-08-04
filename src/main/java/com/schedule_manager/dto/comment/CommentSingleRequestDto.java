package com.schedule_manager.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSingleRequestDto {
    private UUID memberId;
    private Long scheduleId;
    private String content;
}
