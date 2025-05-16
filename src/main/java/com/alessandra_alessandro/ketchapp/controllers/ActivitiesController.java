package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivitiesController {
    private final ActivitiesRepository activitiesRepository;

    @Autowired
    public ActivitiesController(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    private ActivityDto convertEntityToDto(ActivityEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ActivityDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getTomatoId(),
                entity.getType(),
                entity.getAction(),
                entity.getCreatedAt()
        );
    }

    private ActivityEntity convertDtoToEntity(ActivityDto dto) {
        if (dto == null) {
            return null;
        }
        return new ActivityEntity(
                dto.getId(),
                dto.getUserUUID(),
                dto.getTomatoId(),
                dto.getType(),
                dto.getAction(),
                dto.getCreatedAt()
        );
    }

    public List<ActivityDto> getAllActivities() {
        List<ActivityEntity> activities = activitiesRepository.findAll();
        return activities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
