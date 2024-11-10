package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_user")
@Data
public class GroupUserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private GroupModel group;

    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private UserModel inviter;

    private LocalDateTime joinedAt;

    private LocalDateTime invitationExpiresAt;

    public GroupUserModel() {
    }

    public GroupUserModel(UserModel user, GroupModel group, UserModel inviter, LocalDateTime invitationExpiresAt, LocalDateTime joinedAt) {
        this.user = user;
        this.group = group;
        this.inviter = inviter;
        this.invitationExpiresAt = invitationExpiresAt;
        this.joinedAt = joinedAt;
    }
}
