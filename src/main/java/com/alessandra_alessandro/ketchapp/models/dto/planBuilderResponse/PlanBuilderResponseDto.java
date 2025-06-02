package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseDto {

    private PlanBuilderResponseConfigDto config;

    private List<PlanBuilderResponseCalendarDto> calendar;

    private List<PlanBuilderResponseTomatoesDto> tomatoes;

    private List<PlanBuilderResponseRulesDto> rules;

}
