package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

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

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserModel creator;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CheckInModel> checkIns;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private LockModel lock;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileShareRequestModel> request;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupFileModel> groupFiles;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileHistoryModel> history;

    @Column(length = 200, nullable = false)
    private String name;

    private String extension;

    @Column(nullable = false)
    private String path;

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
