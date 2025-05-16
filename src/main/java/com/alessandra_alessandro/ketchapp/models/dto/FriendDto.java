package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {

    private String id;

    private String userUUID;

    private String friendUUID;

    private Timestamp createdAt;
}
