package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserDto {
    @Getter
    @Setter
    private UUID uuid;
    @Getter
    @Setter
    private Timestamp created_at;

    public UserDto(UUID uuid, Timestamp createdAt) {
        this.uuid = uuid;
        this.created_at = createdAt;
    }
}