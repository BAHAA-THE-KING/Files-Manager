package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "locks")
@Data
@NoArgsConstructor
public class LockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    public LockModel(FileModel file) {
        this.file = file;
    }
}
