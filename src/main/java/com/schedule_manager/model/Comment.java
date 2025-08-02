package com.schedule_manager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Comment(User user, Schedule schedule, String content) {
        this.user = user;
        this.schedule = schedule;
        this.content = content;
    }

    private void updateContent(String content) {
        this.content = content;
    }
}
