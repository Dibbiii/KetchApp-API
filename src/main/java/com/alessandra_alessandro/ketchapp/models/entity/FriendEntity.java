package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "friends")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendEntity {
    @Id
    private String id;

    @Column(name = "user_uuid")
    private String userUUID;

    @Column(name = "friend_uuid")
    private String friendUUID;
}
