package com.schedule_manager.service;

import com.schedule_manager.dto.member.*;
import com.schedule_manager.model.Member;
import com.schedule_manager.repository.MemberRepository;
import com.schedule_manager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private MemberResponseDto makeMemberResponseDto(Member savedMember) {
        return MemberResponseDto.builder().
                id(savedMember.getId()).
                username(savedMember.getUsername()).
                nickname(savedMember.getNickname()).
                email(savedMember.getEmail()).
                createdAt(savedMember.getCreatedAt()).
                updatedAt(savedMember.getUpdatedAt()).
                build();
    };

    @Transactional
    public MemberResponseDto updateUser(UUID authenticatedUserId, MemberUpdateRequestDto dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        Member member = memberRepository.findById(authenticatedUserId)
                .orElseThrow(IllegalStateException::new);

        member.updateNickname(dto.getNickname());
        member.updateEmail(dto.getEmail());

        Member savedMember = memberRepository.save(member);

        return makeMemberResponseDto(savedMember);
    }

    @Transactional
    public MemberResponseDto changeUserPassword(UUID authenticatedUserId, MemberPasswordChangeRequestDto dto) {
        Member member = memberRepository.findById(authenticatedUserId)
                .orElseThrow(IllegalStateException::new);

        String encodedOriginalPassword = passwordEncoder.encode(dto.getOriginalPassword());
        if (!passwordEncoder.matches(encodedOriginalPassword, member.getPassword())) {
            throw new BadCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedToChangePassword = passwordEncoder.encode(dto.getToChangePassword());
        if (passwordEncoder.matches(encodedToChangePassword, encodedOriginalPassword)) {
            throw new BadCredentialsException("변경하려는 비밀번호가 현재 비밀번호와 일치합니다.");
        }

        member.updatePassword(encodedToChangePassword);

        Member savedMember = memberRepository.save(member);

        return makeMemberResponseDto(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(UUID id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return makeMemberResponseDto(member);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findByUsername(String username) {
        return makeMemberResponseDto(
                memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(this::makeMemberResponseDto)
                .collect(Collectors.toList());
    }
}
