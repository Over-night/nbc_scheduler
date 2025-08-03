package com.schedule_manager.repository;

import com.schedule_manager.model.Schedule;
import com.schedule_manager.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByMember(Member member);
    List<Schedule> findByTitle(Schedule schedule);

    List<Schedule> findByTitleContaining(String title);
}
