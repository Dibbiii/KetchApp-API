package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    private String id;

    private UUID userUUID;

    private UUID friendUUID;

    private Timestamp createdAt;
}
