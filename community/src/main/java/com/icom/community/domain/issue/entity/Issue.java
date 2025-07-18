package com.icom.community.domain.issue.entity;

import com.icom.community.domain.project.entity.Project;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "TBL_PROJECT_ISSUE_INFO")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Issue(Project project, String userName, String subject, String content) {
        this.project = project;
        this.userName = userName;
        this.subject = subject;
        this.content = content;
    }


}