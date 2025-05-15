package com.alessandra_alessandro.ketchapp.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

public class FriendEntity {
    @Getter
    @Setter
    private UUID user_uuid;
    @Getter
    @Setter
    private UUID friend_uuid;
    @Getter
    @Setter
    private Timestamp created_at;
}
