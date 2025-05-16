package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "tomatoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TomatoEntity {
    @Id
    private Integer id;

    @Column(name = "user_uuid")
    private UUID usreUUID;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @Column(name = "pause_at")
    private Timestamp pauseAt;

    @Column(name = "next_tomato_id")
    private Integer nextTomatoId;

    private String subject;
}
