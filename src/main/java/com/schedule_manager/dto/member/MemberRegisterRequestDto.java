package com.schedule_manager.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    @NotBlank(message = "필수 입력 정보")
    @Size(min = 4, max = 50, message = "길이제한 : 4 <= x <= 50")
    private String username;

    @Size(min = 4, max = 50, message = "길이제한 : 4 <= x <= 50")
    private String nickname;

    @Email
    private String email;

    @NotBlank(message = "필수 입력 정보")
    @Size(min = 6, max = 255, message = "길이제한 : 6 <= x <= 255")
    private String password;
}
