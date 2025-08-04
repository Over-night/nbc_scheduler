package com.schedule_manager.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReviseRequestDto {
    @NotBlank(message = "내용 기입 필수")
    @Size(max = 1000, message = "길이제한: x <= 1000")
    private String content;
}
