package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseSubjectsDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String duration;

}
