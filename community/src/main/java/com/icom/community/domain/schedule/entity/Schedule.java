package com.icom.community.domain.schedule.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "TBL_SCHEDULE_INFO")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="schedule_id")
    private long id;

    @Column(nullable = false)
    private long projectId;

    @Column(nullable = false)
    private String projactName;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String status;

    @Column
    private LocalDateTime scheduleStartTm;

    @Column
    private LocalDateTime scheduleEndTm;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Schedule(long projectId, String projactName, String content, String status,
                    LocalDateTime scheduleStartTm, LocalDateTime scheduleEndTm) {
        this.projectId = projectId;
        this.projactName = projactName;
        this.content = content;
        this.status = status;
        this.scheduleStartTm = scheduleStartTm;
        this.scheduleEndTm = scheduleEndTm;
    }
}
