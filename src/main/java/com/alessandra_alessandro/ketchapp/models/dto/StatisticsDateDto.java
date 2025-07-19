package com.alessandra_alessandro.ketchapp.models.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDateDto {
    @NotNull(message = "{statistics.date.notnull}")
    private LocalDate date;

    @NotNull(message = "{statistics.hours.notnull}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{statistics.hours.min}")
    private Double hours;

    @NotNull(message = "{statistics.subjects.notnull}")
    @Size(min = 1, message = "{statistics.subjects.size}")
    @Valid
    private List<StatisticsSubjectDto> subjects;
}
