package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    private String name;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public AppointmentEntity(UUID userUUID, String name, Timestamp startAt, Timestamp endAt) {
        this.userUUID = userUUID;
        this.name = name;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
