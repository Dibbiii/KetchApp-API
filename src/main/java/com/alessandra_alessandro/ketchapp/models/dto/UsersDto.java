package com.alessandra_alessandro.ketchapp.models.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {

    private UUID id;
    private String username;
    private Double totalHours;
}
