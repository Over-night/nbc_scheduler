package com.schedule_manager.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUploadRequestDto {
    @NotBlank(message = "제목 필수")
    @Size(max = 100, message = "길이제한: x <= 100")
    private String title;

    @Size(max = 1000, message = "길이제한: x <= 1000")
    private String content;
}
