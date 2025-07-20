package com.alessandra_alessandro.ketchapp.models.entity;

import com.alessandra_alessandro.ketchapp.models.enums.ActivityAction;
import com.alessandra_alessandro.ketchapp.models.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uuid")
    private UUID userUUID;

    @Column(name = "tomato_id")
    private Integer tomatoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ActivityType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private ActivityAction action;

    @Column(name = "created_at")
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public ActivityEntity(UUID id, Integer tomatoId, ActivityType type, ActivityAction action) {
        this.userUUID = id;
        this.tomatoId = tomatoId;
        this.type = type;
        this.action = action;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
