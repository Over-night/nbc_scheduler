package com.schedule_manager.service;

import com.schedule_manager.dto.user.UserPasswordChangeRequestDto;
import com.schedule_manager.dto.user.UserResponseDto;
import com.schedule_manager.dto.user.UserRegisterRequestDto;
import com.schedule_manager.dto.user.UserUpdateRequestDto;
import com.schedule_manager.model.User;
import com.schedule_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserResponseDto makeUserResponseDto(User savedUser) {
        return UserResponseDto.builder().
                id(savedUser.getId()).
                username(savedUser.getUsername()).
                nickname(savedUser.getNickname()).
                email(savedUser.getEmail()).
                createdAt(savedUser.getCreatedAt()).
                updatedAt(savedUser.getUpdatedAt()).
                deletedAt(savedUser.getDeletedAt()).
                build();
    };

    @Transactional
    public UserResponseDto registerUser(UserRegisterRequestDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 유저 ID");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        User user = User.builder()
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();

        User savedUser = userRepository.save(user);


        return makeUserResponseDto(savedUser);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(IllegalStateException::new);

        user.updateNickname(dto.getNickname());

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder().
                id(savedUser.getId()).
                username(savedUser.getUsername()).
                nickname(savedUser.getNickname()).
                email(savedUser.getEmail()).
                createdAt(savedUser.getCreatedAt()).
                updatedAt(savedUser.getUpdatedAt()).
                deletedAt(savedUser.getDeletedAt()).
                build();
    }

    @Transactional
    public UserResponseDto changeUserPassword(UserPasswordChangeRequestDto dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(IllegalStateException::new);

        String encodedOriginalPassword = passwordEncoder.encode(dto.getOriginalPassword());
        if (!passwordEncoder.matches(encodedOriginalPassword, user.getPassword())) {
            throw new BadCredentialsException("현재 비밀번호가 일치하지 않습니다.");
        }

        String encodedToChangePassword = passwordEncoder.encode(dto.getToChangePassword());
        if (passwordEncoder.matches(encodedToChangePassword, encodedOriginalPassword)) {
            throw new BadCredentialsException("변경하려는 비밀번호가 현재 비밀번호와 일치합니다.");
        }

        user.updatePassword(encodedToChangePassword);

        User savedUser = userRepository.save(user);

        return makeUserResponseDto(savedUser);
    }

    // TODO : 사용할곳 찾고 경우에 따라 반환
    @Transactional(readOnly = true)
    public UserResponseDto findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return UserResponseDto.builder().
                id(user.getId()).
                username(user.getUsername()).
                nickname(user.getNickname()).
                email(user.getEmail()).
                createdAt(user.getCreatedAt()).
                updatedAt(user.getUpdatedAt()).
                deletedAt(user.getDeletedAt()).
                build();
    }
}
