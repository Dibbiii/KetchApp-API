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
}
