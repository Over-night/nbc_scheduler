package com.schedule_manager.dto.schedule;

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
public class ScheduleReviseResponseDto {
    private Long id;
    private UUID memberId;
    private String title;
    private String content;
    private LocalDateTime updatedAt;
}
