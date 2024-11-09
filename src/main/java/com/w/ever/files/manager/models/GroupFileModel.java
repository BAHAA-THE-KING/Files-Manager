package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_file")
@Data
public class GroupFileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupModel group;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    @Column(nullable = false)
    private LocalDateTime addedAt;

    public GroupFileModel() {
    }

    public GroupFileModel(GroupModel group, FileModel file, LocalDateTime addedAt) {
        this.group = group;
        this.file = file;
        this.addedAt = addedAt;
    }
}
