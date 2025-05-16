package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private UUID uuid;

    private String username;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public UserEntity(String username) {
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}