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
    private String username;
    @Getter
    @Setter
    private Timestamp createdAt;

    public UserDto(UUID uuid, String username, Timestamp createdAt) {
        this.uuid = uuid;
        this.username = username;
        this.createdAt = createdAt;
    }
}