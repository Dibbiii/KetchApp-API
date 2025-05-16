package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "achievement_id")
    private Integer achievementId;

    @Column(name = "created_at")
    private Timestamp createdAt;
}
