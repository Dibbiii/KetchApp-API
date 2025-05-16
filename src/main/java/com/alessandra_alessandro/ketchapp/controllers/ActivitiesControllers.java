package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public ActivityDto getActivityByUserUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<ActivityEntity> activity = activitiesRepository.findByUserUUID(uuid);
        return convertEntityToDto(activity.orElse(null));
    }

    public ActivityDto getActivityById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<ActivityEntity> activity = activitiesRepository.findById(id);
        return convertEntityToDto(activity.orElse(null));
    }

    public List<ActivityDto> getActivitiesByTomatoId(Integer tomatoId) {
        if (tomatoId == null) {
            throw new IllegalArgumentException("Tomato ID cannot be null");
        }
        Optional<ActivityEntity> activities = activitiesRepository.findByTomatoId(tomatoId);
        return activities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ActivityDto createActivity(ActivityDto activityDto) {
        if (activityDto == null || activityDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Activity data or user UUID cannot be null");
        }
        ActivityEntity entityToSave = convertDtoToEntity(activityDto);
        ActivityEntity savedEntity = activitiesRepository.save(entityToSave);
        return convertEntityToDto(savedEntity);
    }

    public void deleteActivityById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        activitiesRepository.deleteById(id);
    }

}
