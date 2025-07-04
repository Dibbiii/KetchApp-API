package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "achievements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private Boolean completed;

    @Column(name = "icon")
    private String icon;

    public AchievementEntity(UUID userUUID, String description, Boolean completed) {
        this.userUUID = userUUID;
        this.description = description;
        this.completed = completed;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.icon = "default_icon.png"; // Default icon if not provided
    }

    public AchievementEntity(UUID userUUID, String description, Boolean completed, String icon) {
        this.userUUID = userUUID;
        this.description = description;
        this.completed = completed;
        this.icon = icon;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
