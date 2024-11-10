package com.w.ever.files.manager.models;

import com.w.ever.files.manager.enums.RequestStatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_share_requests")
@Data
@NoArgsConstructor
public class FileShareRequestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatusEnum status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", nullable = false)
    private FileModel file;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupModel group;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    public FileShareRequestModel(RequestStatusEnum status, FileModel file, UserModel user, GroupModel group, LocalDateTime expiresAt, LocalDateTime requestedAt) {
        this.status = status;
        this.file = file;
        this.user = user;
        this.group = group;
        this.expiresAt = expiresAt;
        this.requestedAt = requestedAt;
    }
}
