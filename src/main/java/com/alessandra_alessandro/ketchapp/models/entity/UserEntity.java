package com.alessandra_alessandro.ketchapp.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserEntity {
    @Setter
    @Getter
    private UUID uuid;
    @Setter
    @Getter
    private Timestamp created_at;

    public UserEntity(UUID uuid, Timestamp created_at) {
        this.uuid = uuid;
        this.created_at = created_at;
    }
}