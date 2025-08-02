package com.schedule_manager.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {
    private String username;
    private String nickname;
    private String email;
    private String password;
}
