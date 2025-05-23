package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto implements Serializable {

    private Integer id;

    private Timestamp createdAt;

}