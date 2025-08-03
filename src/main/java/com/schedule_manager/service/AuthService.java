package com.schedule_manager.service;

import com.schedule_manager.auth.dto.JwtTokenDto;
import com.schedule_manager.dto.member.MemberRegisterRequestDto;
import com.schedule_manager.dto.member.MemberRegisterResponseDto;
import com.schedule_manager.model.Member;
import com.schedule_manager.repository.MemberRepository;
import com.schedule_manager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public JwtTokenDto login(String username, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성 및 반환
        return jwtTokenProvider.generateToken(authentication);
    }

    @Transactional
    public MemberRegisterResponseDto register(MemberRegisterRequestDto dto) {
        if (memberRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 유저 ID");
        }
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Member member = Member.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();

        Member savedMember = memberRepository.save(member);


        return MemberRegisterResponseDto.builder().
                id(savedMember.getId()).
                username(savedMember.getUsername()).
                nickname(savedMember.getNickname()).
                email(savedMember.getEmail()).
                createdAt(savedMember.getCreatedAt()).
                build();
    }
}
