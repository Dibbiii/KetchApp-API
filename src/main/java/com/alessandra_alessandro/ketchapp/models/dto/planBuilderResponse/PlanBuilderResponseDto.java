package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseDto {

    @NotNull
    private UUID userUUID;

    @NotEmpty
    private String session;

    @NotEmpty
    private String breakDuration;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<PlanBuilderResponseCalendarDto> calendar;

    @Valid
    @NotNull
    @Size(min = 1)
    private List<PlanBuilderResponseSubjectsDto> subjects;

}
