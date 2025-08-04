package com.schedule_manager.dto.member;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordChangeRequestDto {
    @Size(min = 6, max = 255)
    private String originalPassword;

    @Size(min = 6, max = 255)
    private String toChangePassword;
}
