package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.LocalDateTime;

@Entity
@Table(name = "check_ins")
@Data
public class CheckInModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    @OneToOne(mappedBy = "checkIn", cascade = CascadeType.ALL, orphanRemoval = true)
    private FileHistoryModel fileHistory;

    @Column(nullable = false)
    private LocalDateTime checkedInAt;

    private LocalDateTime checkedOutAt;

    public CheckInModel() {
    }

    public CheckInModel(UserModel user, FileModel file, LocalDateTime checkedInAt, LocalDateTime checkedOutAt) {
        this.user = user;
        this.file = file;
        this.checkedInAt = checkedInAt;
        this.checkedOutAt = checkedOutAt;
    }
}
