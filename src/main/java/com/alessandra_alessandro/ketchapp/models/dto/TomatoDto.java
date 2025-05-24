package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TomatoDto implements Serializable {

    private Integer id;

    private UUID userUUID;

    private Integer groupId;

    private Timestamp startAt;

    private Timestamp endAt;

    private Timestamp pauseEnd;

    private Integer nextTomatoId;

    private String subject;

    private Timestamp createdAt;
}