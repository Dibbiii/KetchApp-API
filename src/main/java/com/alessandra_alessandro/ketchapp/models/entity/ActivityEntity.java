package com.alessandra_alessandro.ketchapp.models.entity;

import com.alessandra_alessandro.ketchapp.models.enums.ActivityAction;
import com.alessandra_alessandro.ketchapp.models.enums.ActivityType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

public class ActivityEntity {
    @Getter
    @Setter
    private UUID user_uuid;
    @Getter
    @Setter
    private Integer tomato_id;
    @Getter
    @Setter
    private Timestamp created_at;
    @Getter
    @Setter
    private ActivityType type;
    @Getter
    @Setter
    private ActivityAction action;
}
