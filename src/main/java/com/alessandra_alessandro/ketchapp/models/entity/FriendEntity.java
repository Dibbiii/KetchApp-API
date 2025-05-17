package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "friend_uuid")
    private UUID friendUUID;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public FriendEntity(UUID userUUID, UUID friendUUID) {
        this.userUUID = userUUID;
        this.friendUUID = friendUUID;
    }
}
