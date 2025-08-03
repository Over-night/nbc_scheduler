package com.schedule_manager.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String password;
}
