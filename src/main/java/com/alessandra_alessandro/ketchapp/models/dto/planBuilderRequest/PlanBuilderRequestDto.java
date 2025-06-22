package com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderRequestDto {

    private List<PlanBuilderRequestCalendarDto> calendar;

    private List<PlanBuilderRequestTomatoesDto> tomatoes;
}