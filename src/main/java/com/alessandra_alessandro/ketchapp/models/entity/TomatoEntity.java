package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "start_at")
    private Timestamp startAt;

    @Column(name = "end_at")
    private Timestamp endAt;

    @Column(name = "pause_end")
    private Timestamp pauseEnd;

    @Column(name = "next_tomato_id")
    private Integer nextTomatoId;

    private String subject;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public TomatoEntity(UUID userUUID, Integer groupId, Timestamp startAt, Timestamp endAt, Timestamp pauseEnd, Integer nextTomatoId, String subject) {
        this.userUUID = userUUID;
        this.groupId = groupId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.pauseEnd = pauseEnd;
        this.nextTomatoId = nextTomatoId;
        this.subject = subject;
    }

}