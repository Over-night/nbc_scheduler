package com.schedule_manager.repository;

import com.schedule_manager.model.Comment;
import com.schedule_manager.model.Schedule;
import com.schedule_manager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMember(Member member);
    List<Comment> findBySchedule(Schedule schedule);
}
