package com.icom.community.domain.document.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name="TBL_DOC_INFO")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="folder_id")
    private Folder folder;

    @Column(nullable = false)
    private String docType;

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String uploaderId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
