package com.alessandra_alessandro.ketchapp.models.dto;

import jakarta.validation.constraints.*;
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
public class AchievementDto {

    @NotNull(message = "{achievement.id.notnull}")
    private Integer id;

    @NotNull(message = "{achievement.userUUID.notnull}")
    private UUID userUUID;

    @NotBlank(message = "{achievement.description.notblank}")
    @Size(max = 255, message = "{achievement.description.size}")
    private String description;

    @NotNull(message = "{achievement.completed.notnull}")
    private Boolean completed;

    @NotNull(message = "{achievement.createdAt.notnull}")
    @PastOrPresent(message = "{achievement.createdAt.pastorpresent}")
    private Timestamp createdAt;
}