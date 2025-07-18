package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseCalendarDto {

    @NotEmpty
    private String start_at;

    @NotEmpty
    private String end_at;

    @NotEmpty
    private String title;
}
