package com.alessandra_alessandro.ketchapp.models.dto.planBuilderRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanBuilderRequestCalendarDto {

    private String title;

    private String start_at;

    private String end_at;

}
