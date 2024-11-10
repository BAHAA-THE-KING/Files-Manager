package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups_table")
@Data
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private UserModel creator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupUserModel> joinedUsers = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileShareRequestModel> requests = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupFileModel> groupFiles = new HashSet<>();

    private String description;

    private String color;

    private String lang;

    public GroupModel() {
    }

    public GroupModel(String name, UserModel creator, String description, String color, String lang) {
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.color = color;
        this.lang = lang;
    }
}
