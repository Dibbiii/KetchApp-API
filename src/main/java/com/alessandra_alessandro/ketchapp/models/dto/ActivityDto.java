package com.alessandra_alessandro.ketchapp.models.dto;

import com.alessandra_alessandro.ketchapp.models.enums.ActivityAction;
import com.alessandra_alessandro.ketchapp.models.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    @NotNull(message = "{activity.id.notnull}")
    private Integer id;

    @NotNull(message = "{activity.userUUID.notnull}")
    private UUID userUUID;

    @NotNull(message = "{activity.tomatoId.notnull}")
    private Integer tomatoId;

    @NotNull(message = "{activity.type.notnull}")
    private ActivityType type;

    @NotNull(message = "{activity.action.notnull}")
    private ActivityAction action;

    @NotNull(message = "{activity.createdAt.notnull}")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
}
