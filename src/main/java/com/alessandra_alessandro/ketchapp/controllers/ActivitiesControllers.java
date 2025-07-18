package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivitiesControllers {
    private final ActivitiesRepository activitiesRepository;

    @Autowired
    public ActivitiesControllers(ActivitiesRepository activitiesRepository) {
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
                dto.getUserUUID(),
                dto.getTomatoId(),
                dto.getType(),
                dto.getAction()
        );
    }

    public ActivityDto createActivity(ActivityDto activityDto) {
        if (activityDto == null || activityDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Activity data or user UUID cannot be null");
        }
        ActivityEntity activityEntity = convertDtoToEntity(activityDto);
        activityEntity = activitiesRepository.save(activityEntity);
        return convertEntityToDto(activityEntity);
    }
}