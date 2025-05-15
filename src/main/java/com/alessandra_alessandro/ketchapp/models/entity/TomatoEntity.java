package com.alessandra_alessandro.ketchapp.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

public class TomatoEntity {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private UUID user_uuid;
    @Getter
    @Setter
    private Integer group_id;
    @Getter
    @Setter
    private Timestamp created_at;
    @Getter
    @Setter
    private Timestamp start_at;
    @Getter
    @Setter
    private Timestamp end_at;
    @Getter
    @Setter
    private Timestamp pause_at;
    @Getter
    @Setter
    private Integer next_tomato_id;
    @Getter
    @Setter
    private String subject;
}
