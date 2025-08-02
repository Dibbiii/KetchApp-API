package com.alessandra_alessandro.ketchapp.models.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    private UUID id;
    private String username;
    private String email;
}
