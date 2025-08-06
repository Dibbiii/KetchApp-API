package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String email;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public UserEntity(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public UserEntity(UUID uuid, String username) {
        this.id = uuid;
        this.username = username;
    }

    public UserEntity(UUID uuid, String username, String email) {
        this.id = uuid;
        this.username = username;
        this.email = email;
    }
}
