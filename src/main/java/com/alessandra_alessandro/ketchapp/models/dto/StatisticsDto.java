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
public class StatisticsDto {
    @NotNull(message = "{statistics.dates.notnull}")
    @Size(min = 1, message = "{statistics.dates.size}")
    @Valid
    private List<StatisticsDateDto> dates;
}
