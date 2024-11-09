package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_history", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"file", "version"})
})
@Data
@NoArgsConstructor
public class FileHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    @OneToOne
    @JoinColumn(name = "check_in_id", nullable = false)
    private CheckInModel checkIn;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public FileHistoryModel(FileModel file, CheckInModel checkIn, String version, String path, LocalDateTime createdAt) {
        this.file = file;
        this.checkIn = checkIn;
        this.version = version;
        this.path = path;
        this.createdAt = createdAt;
    }
}
