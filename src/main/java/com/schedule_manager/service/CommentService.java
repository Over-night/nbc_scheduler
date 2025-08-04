package com.schedule_manager.service;

import com.schedule_manager.dto.comment.*;
import com.schedule_manager.dto.schedule.*;
import com.schedule_manager.model.Comment;
import com.schedule_manager.model.Member;
import com.schedule_manager.model.Schedule;
import com.schedule_manager.repository.CommentRepository;
import com.schedule_manager.repository.MemberRepository;
import com.schedule_manager.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    private CommentResponseDto makeCommentResponse(Comment savedComment) {
        return CommentResponseDto.builder().
                id(savedComment.getId()).
                memberId(savedComment.getMember().getId()).
                scheduleId(savedComment.getSchedule().getId()).
                content(savedComment.getContent()).
                createdAt(savedComment.getCreatedAt()).
                updatedAt(savedComment.getUpdatedAt()).
                build();
    };

    @Transactional
    public CommentUploadResponseDto uploadComment(Long id, String username, CommentUploadRequestDto dto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);


        Comment comment = Comment.builder().
                member(member).
                schedule(schedule).
                content(dto.getContent()).
                build();

        Comment savedComment = commentRepository.save(comment);

        return CommentUploadResponseDto.builder().
                id(savedComment.getId()).
                memberId(savedComment.getMember().getId()).
                scheduleId(savedComment.getSchedule().getId()).
                content(savedComment.getContent()).
                createdAt(savedComment.getCreatedAt()).
                build();
    }

    @Transactional
    public CommentReviseResponseDto reviseComment(Long scheduleId, Long commentId, String username, CommentReviseRequestDto dto) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalStateException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalStateException::new);


        if (!comment.getSchedule().getId().equals(schedule.getId())) {
            throw new BadCredentialsException("유효하지 않은 명령입니다.");
        }
        if (comment.getDeletedAt() != null) {
            throw new BadCredentialsException("삭제된 댓글입니다.");
        }
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new BadCredentialsException("작성자만 수정할 수 있습니다.");
        }

        comment.update(dto.getContent());

        return CommentReviseResponseDto.builder().
                id(comment.getId()).
                memberId(comment.getMember().getId()).
                scheduleId(comment.getSchedule().getId()).
                content(schedule.getContent()).
                updatedAt(schedule.getUpdatedAt()).
                build();
    }

    @Transactional
    public CommentDeleteResponseDto deleteComment(Long scheduleId, Long commentId, String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(IllegalStateException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(IllegalStateException::new);

        if (!comment.getSchedule().getId().equals(schedule.getId())) {
            throw new BadCredentialsException("유효하지 않은 명령입니다.");
        }
        if (comment.getDeletedAt() != null) {
            throw new BadCredentialsException("이미 삭제된 댓글입니다.");
        }
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new BadCredentialsException("작성자만 수정할 수 있습니다.");
        }

        comment.delete();

        return CommentDeleteResponseDto.builder().
                id(schedule.getId()).
                deletedAt(schedule.getUpdatedAt()).
                build();
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long id) {
        return makeCommentResponse(
                commentRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByUsername(String username) {
        Member owner = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findByMember(owner)
                .stream()
                .filter(comment -> comment.getDeletedAt() == null)
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByScheduleId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findBySchedule(schedule)
                .stream()
                .filter(comment -> comment.getDeletedAt() == null)
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByUsernameIncludingDeleted(String username) {
        Member owner = memberRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findByMember(owner)
                .stream()
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByScheduleIdIncludingDeleted(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        return commentRepository.findBySchedule(schedule)
                .stream()
                .map(this::makeCommentResponse)
                .collect(Collectors.toList());
    }
}
