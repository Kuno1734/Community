package com.icom.community.domain.document.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name="TBL_FOLDER_INFO")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="folder_id")
    private long id;

    @Column
    private Long parentId;

    @Column(nullable = false)
    private String folderName;

    @Column(nullable = false)
    private int forderDept;

    @OneToMany(mappedBy = "folder" , fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
