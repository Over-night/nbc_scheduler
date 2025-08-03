package com.schedule_manager.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordChangeRequestDto {
    private String originalPassword;
    private String toChangePassword;
}
