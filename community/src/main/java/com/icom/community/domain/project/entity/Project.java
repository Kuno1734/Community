package com.icom.community.domain.project.entity;


import com.icom.community.domain.issue.entity.Issue;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TBL_PROJECT_INFO")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private long id;

    @Column(nullable = false)
    private String projectName;

    @Column(nullable = false)
    private char projectStatus;

    @Column(nullable = false)
    private String managerName;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime projectStartTm;

    @Column
    private LocalDateTime projectEndTm;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Issue> issueList = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @Builder
    public Project(String projectName, char projectStatus, String managerName, String content, LocalDateTime projectStartTm,
                   LocalDateTime projectEndTm, List<Issue> issueList) {
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.managerName = managerName;
        this.content = content;
        this.projectStartTm = projectStartTm;
        this.projectEndTm = projectEndTm;
        this.issueList = issueList;
    }

    public void addIssue(Issue issue) {
        issueList.add(issue);
        issue.setProject(this);
    }

}
