package com.schedule_manager.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedules")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Schedule(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = (content != null) ? content : "";
    }

    private void updateTitle(String title) {
        this.title = title;
    }
    private void updateContent(String content) {
        this.content = content;
    }
}
