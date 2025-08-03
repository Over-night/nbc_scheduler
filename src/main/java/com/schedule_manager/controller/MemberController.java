package com.schedule_manager.controller;

import com.schedule_manager.auth.dto.JwtTokenDto;
import com.schedule_manager.dto.member.MemberLoginRequestDto;
import com.schedule_manager.dto.member.MemberRegisterRequestDto;
import com.schedule_manager.dto.member.MemberRegisterResponseDto;
import com.schedule_manager.dto.member.MemberResponseDto;
import com.schedule_manager.repository.MemberRepository;
import com.schedule_manager.service.AuthService;
import com.schedule_manager.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<MemberRegisterResponseDto> register(@RequestBody MemberRegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllUsers() {
        List<MemberResponseDto> allUsers = memberService.findAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("id/{username}")
    public ResponseEntity<MemberResponseDto> getUserByUsername(@PathVariable String username) {
        MemberResponseDto foundUser = memberService.findByUsername(username);
        return ResponseEntity.ok(foundUser);
    }

    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody MemberLoginRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        return authService.login(username, password);
    }


    // Test
    @PostMapping("/test")
    public String test() {
        return "success";
    }
}
