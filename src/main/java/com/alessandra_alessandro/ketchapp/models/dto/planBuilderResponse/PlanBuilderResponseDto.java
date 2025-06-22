package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseDto {

    private UUID userUUID;

    private PlanBuilderResponseConfigDto config;

    private List<PlanBuilderResponseCalendarDto> calendar;

    private List<PlanBuilderResponseSubjectsDto> subjects;

}
