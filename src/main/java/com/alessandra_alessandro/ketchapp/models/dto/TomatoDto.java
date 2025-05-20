package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TomatoDto {

    private Integer id;

    private UUID userUUID;

    private Integer groupId;

    private Timestamp startAt;

    private Timestamp endAt;

    private Timestamp pauseAt;

    private Integer nextTomatoId;

    private String subject;

    private Timestamp createdAt;

    public TomatoDto(Integer id, UUID userUUID, Object o, Integer groupId, Timestamp startAt, Timestamp endAt, Timestamp pauseAt, Integer nextTomatoId, String subject, Timestamp createdAt) {
        this.id = id;
        this.userUUID = userUUID;
        this.groupId = groupId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.pauseAt = pauseAt;
        this.nextTomatoId = nextTomatoId;
        this.subject = subject;
        this.createdAt = createdAt;
    }
}
