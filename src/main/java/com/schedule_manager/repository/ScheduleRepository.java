package com.schedule_manager.repository;

import com.schedule_manager.model.Schedule;
import com.schedule_manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByUser(User user);
    List<Schedule> findByTitle(Schedule schedule);

    List<Schedule> findByTitleContaining(String title);
}
