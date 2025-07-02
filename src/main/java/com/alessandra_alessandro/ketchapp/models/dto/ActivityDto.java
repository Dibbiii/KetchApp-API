package com.alessandra_alessandro.ketchapp.models.dto;

import com.alessandra_alessandro.ketchapp.models.enums.ActivityAction;
import com.alessandra_alessandro.ketchapp.models.enums.ActivityType;
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
public class ActivityDto {

    private Integer id;

    private UUID userUUID;

    private Integer tomatoId;

    private ActivityType type;

    private ActivityAction action;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
}
