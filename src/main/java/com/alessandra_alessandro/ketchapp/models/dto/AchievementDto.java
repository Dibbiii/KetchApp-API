package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDto {

    private Integer id;

    private UUID userUUID;

    private String description;

    private Boolean completed;

    private Timestamp createdAt;

    private String icon;

    public AchievementDto(Integer id, UUID userUUID, String description, Boolean completed, Timestamp createdAt) {
        this.id = id;
        this.userUUID = userUUID;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }
}