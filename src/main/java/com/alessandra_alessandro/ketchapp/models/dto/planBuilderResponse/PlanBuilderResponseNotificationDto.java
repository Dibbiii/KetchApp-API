package com.alessandra_alessandro.ketchapp.models.dto.planBuilderResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanBuilderResponseNotificationDto {

    private boolean enabled;

    private String sound;

    private boolean vibration;

}
