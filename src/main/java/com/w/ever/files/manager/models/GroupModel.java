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

//    @ManyToOne
//    @JoinColumn(name = "creator_id", nullable = false)
    private Integer creator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupUserModel> joinedUsers = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileShareRequestModel> requests = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupFileModel> groupFiles = new HashSet<>();

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String color;

    @Column(nullable = true)
    private String lang;

    public GroupModel() {
    }

    public GroupModel(String name, UserModel creator, String description, String color, String lang) {
        this.name = name;
        this.creator = creator.getId();
        this.description = description;
        this.color = color;
        this.lang = lang;
    }
}
