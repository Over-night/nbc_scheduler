package com.schedule_manager.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordChangeRequestDto {
    private UUID id;
    private String originalPassword;
    private String toChangePassword;
}
