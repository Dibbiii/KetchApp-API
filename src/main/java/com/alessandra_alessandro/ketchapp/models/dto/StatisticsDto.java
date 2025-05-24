package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDto implements Serializable {

    UUID userUUID;

    Integer focusTime;

    Integer breakTime;

    Integer totalTomatoes;

    Integer completedTomatoes;

    Integer skippedTomatoes;

    Integer interruptedTomatoes;

    Integer totalTime;

    Integer completedTime;

    Integer skippedTime;

    Integer interruptedTime;

    Integer completedBreaks;

    Integer skippedBreaks;

    Integer interruptedBreaks;

    public StatisticsDto(UUID userUUID, Integer totalTomatoes, Integer totalTime) {
    }
}