package com.schedule_manager.dto.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReviseRequestDto {
    private Long id;
    private String title;
    private String content;
}
