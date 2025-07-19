package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull(message = "{user.uuid.notnull}")
    private UUID uuid;

    @NotBlank(message = "{user.username.notblank}")
    private String username;

    @NotNull(message = "{user.totalHours.notnull}")
    private Double totalHours;
}