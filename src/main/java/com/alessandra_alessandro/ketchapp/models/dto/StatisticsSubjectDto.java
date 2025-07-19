package com.alessandra_alessandro.ketchapp.models.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsSubjectDto {
    @NotBlank(message = "{statistics.subject.name.notblank}")
    private String name;

    @NotNull(message = "{statistics.subject.hours.notnull}")
    @DecimalMin(value = "0.0", inclusive = true, message = "{statistics.subject.hours.min}")
    private Double hours;
}
