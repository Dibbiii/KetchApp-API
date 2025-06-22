package com.alessandra_alessandro.ketchapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class StatisticEntity {
    @Id
    @Column(nullable = false)
    private UUID userUUID;

    private Integer focusTime;

    private Integer breakTime;

    private Integer totalTomatoes;

    private Integer completedTomatoes;

    private Integer skippedTomatoes;

    private Integer interruptedTomatoes;

    private Integer totalTime;

    private Integer completedTime;

    private Integer skippedTime;

    private Integer interruptedTime;

    private Integer completedBreaks;

    private Integer skippedBreaks;

    private Integer interruptedBreaks;

}