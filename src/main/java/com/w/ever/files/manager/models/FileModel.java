package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "files")
@Data
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private FileModel parent;

    @OneToMany(mappedBy = "parent")
    private List<FileModel> children;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserModel creator;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckInModel> checkIns;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileShareRequestModel> request;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupFileModel> groupFiles;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileHistoryModel> history;

    @Column(length = 200, nullable = false)
    private String name;

    private String extension;

    @Column(nullable = true)
    private String path;

    @Column(nullable = true)
    private LocalDateTime addedAt;

    public FileModel() {
    }

    public FileModel(String path, String extension, String name, UserModel creator, FileModel parent) {
        this.path = path;
        this.extension = extension;
        this.name = name;
        this.creator = creator;
        this.parent = parent;
    }
}
