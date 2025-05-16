package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

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
    private String userUUID;

    @Column(name = "friend_uuid")
    private String friendUUID;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public FriendEntity(String userUUID, String friendUUID) {
        this.userUUID = userUUID;
        this.friendUUID = friendUUID;
    }
}
