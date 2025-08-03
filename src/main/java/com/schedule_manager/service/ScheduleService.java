package com.schedule_manager.service;

import com.schedule_manager.dto.schedule.ScheduleResponseDto;
import com.schedule_manager.dto.schedule.ScheduleReviseRequestDto;
import com.schedule_manager.dto.schedule.ScheduleUploadRequestDto;
import com.schedule_manager.model.Schedule;
import com.schedule_manager.model.Member;
import com.schedule_manager.repository.ScheduleRepository;
import com.schedule_manager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    private ScheduleResponseDto makeScheduleResponse(Schedule savedSchedule) {
        return ScheduleResponseDto.builder().
                id(savedSchedule.getId()).
                memberId(savedSchedule.getMember().getId()).
                title(savedSchedule.getTitle()).
                content(savedSchedule.getContent()).
                createdAt(savedSchedule.getCreatedAt()).
                updatedAt(savedSchedule.getUpdatedAt()).
                build();
    };

    @Transactional
    public ScheduleResponseDto uploadSchedule(UUID authenticatedUserId, ScheduleUploadRequestDto dto) {
        Member member = memberRepository.findById(authenticatedUserId)
                .orElseThrow(IllegalStateException::new);


        Schedule schedule = Schedule.builder().
                member(member).
                title(dto.getTitle()).
                content(dto.getContent()).
                build();

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return makeScheduleResponse(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto reviseSchedule(UUID authenticatedUserId, ScheduleReviseRequestDto dto) {
        Member member = memberRepository.findById(authenticatedUserId)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(dto.getId())
                .orElseThrow(IllegalStateException::new);

        // 현재는 작성자만 사용가능
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new BadCredentialsException("삭제 권한이 없습니다.");
        }

        schedule.updateTitle(dto.getTitle());
        schedule.updateContent(dto.getContent());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return makeScheduleResponse(savedSchedule);
    }

    @Transactional(readOnly = true)
    public ScheduleResponseDto findById(Long id) {
        return makeScheduleResponse(
                scheduleRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findByUsername(String username) {
        Member owner = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);

        return scheduleRepository.findByMember(owner)
                .stream()
                .map(this::makeScheduleResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::makeScheduleResponse)
                .collect(Collectors.toList());
    }
}
