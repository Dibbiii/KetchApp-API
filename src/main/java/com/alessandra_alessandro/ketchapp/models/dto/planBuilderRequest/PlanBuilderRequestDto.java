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

    private List<PlanBuilderRequestSubjectsDto> subjects;

    public String toJson() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(this);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new RuntimeException("Error converting PlanBuilderRequestDto to JSON", e);
        }
    }
}