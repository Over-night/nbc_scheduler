package com.schedule_manager.service;

import com.schedule_manager.dto.comment.CommentResponseDto;
import com.schedule_manager.dto.schedule.*;
import com.schedule_manager.model.Schedule;
import com.schedule_manager.model.Member;
import com.schedule_manager.repository.ScheduleRepository;
import com.schedule_manager.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;

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
    public ScheduleResponseDto uploadSchedule(String username, ScheduleUploadRequestDto dto) {
        Member member = memberRepository.findByUsername(username)
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
    public ScheduleReviseResponseDto reviseSchedule(Long id, String username, ScheduleReviseRequestDto dto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        // 현재는 작성자만 사용가능
        if (schedule.getDeletedAt() != null) {
            throw new BadCredentialsException("이미 삭제된 스케쥴입니다.");
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new BadCredentialsException("작성자만 수정할 수 있습니다.");
        }

        schedule.update(dto.getTitle(), dto.getContent());

        return ScheduleReviseResponseDto.builder().
                id(schedule.getId()).
                memberId(schedule.getMember().getId()).
                title(schedule.getTitle()).
                content(schedule.getContent()).
                updatedAt(schedule.getUpdatedAt()).
                build();
    }

    @Transactional
    public ScheduleDeleteResponseDto deleteSchedule(Long id, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        // 현재는 작성자만 사용가능
        if (schedule.getDeletedAt() != null) {
            throw new BadCredentialsException("이미 삭제된 스케쥴입니다.");
        }
        if (!schedule.getMember().getId().equals(member.getId())) {
            throw new BadCredentialsException("작성자만 삭제할 수 있습니다.");
        }

        schedule.delete();

        return ScheduleDeleteResponseDto.builder().
                id(schedule.getId()).
                memberId(schedule.getMember().getId()).
                deletedAt(schedule.getUpdatedAt()).
                build();
    }

    @Transactional(readOnly = true)
    public ScheduleWithCommentResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("찾으려는 스케쥴이 없습니다."));

        if(schedule.getDeletedAt() != null) {
            throw new BadCredentialsException("이미 삭제된 스케쥴입니다.");
        }

        List<CommentResponseDto> comments = schedule.getComments().stream()
                .map(comment -> CommentResponseDto.builder().
                        id(comment.getId()).
                        memberId(comment.getMember().getId()).
                        scheduleId(comment.getSchedule().getId()).
                        content(comment.getContent()).
                        createdAt(comment.getCreatedAt()).
                        updatedAt(comment.getUpdatedAt()).
                        build()
                )
                .collect(Collectors.toList());

        return ScheduleWithCommentResponseDto.builder().
                id(schedule.getId()).
                memberId(schedule.getMember().getId()).
                title(schedule.getTitle()).
                content(schedule.getContent()).
                createdAt(schedule.getCreatedAt()).
                updatedAt(schedule.getUpdatedAt()).
                comments(comments).
                build();
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findByUsername(String username) {
        Member owner = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);

        return scheduleRepository.findByMember(owner)
                .stream()
                .filter(schedule -> schedule.getDeletedAt() == null)
                .map(this::makeScheduleResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findByUsernameIncludingDeleted(String username) {
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
                .filter(schedule -> schedule.getDeletedAt() == null)
                .map(this::makeScheduleResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> findAllIncludingDeleted() {
        return scheduleRepository.findAll()
                .stream()
                .map(this::makeScheduleResponse)
                .collect(Collectors.toList());
    }
}
