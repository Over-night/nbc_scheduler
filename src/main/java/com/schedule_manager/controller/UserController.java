package com.schedule_manager.controller;

import com.schedule_manager.dto.user.UserResponseDto;
import com.schedule_manager.model.User;
import com.schedule_manager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class UserController {
    private UserService userService;




    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{username}")
    public UserResponseDto getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

}
