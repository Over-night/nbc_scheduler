package com.schedule_manager.dto.member;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String username;
    private String password;
}
