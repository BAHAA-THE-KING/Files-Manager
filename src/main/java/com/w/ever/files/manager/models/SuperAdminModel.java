package com.w.ever.files.manager.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "super_admins")
@Data
public class SuperAdminModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserModel user;
}
