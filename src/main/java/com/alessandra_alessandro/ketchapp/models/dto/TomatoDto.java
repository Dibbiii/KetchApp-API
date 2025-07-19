package com.alessandra_alessandro.ketchapp.models.dto;

import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TomatoDto implements Serializable {

    @NotNull(message = "{tomato.id.notnull}")
    private Integer id;

    @NotNull(message = "{tomato.userUUID.notnull}")
    private UUID userUUID;

    @NotNull(message = "{tomato.startAt.notnull}")
    private Timestamp startAt;

    @NotNull(message = "{tomato.endAt.notnull}")
    private Timestamp endAt;

    @NotNull(message = "{tomato.pauseEnd.notnull}")
    private Timestamp pauseEnd;

    private Integer nextTomatoId;

    @NotBlank(message = "{tomato.subject.notblank}")
    private String subject;

    @NotNull(message = "{tomato.createdAt.notnull}")
    private Timestamp createdAt;
}
