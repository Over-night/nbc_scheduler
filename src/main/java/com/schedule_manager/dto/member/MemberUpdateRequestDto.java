package com.schedule_manager.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {
    @Email
    private String email;

    @Size(min = 4, max = 50, message = "길이제한 : 4 <= x <= 50")
    private String nickname;
}
