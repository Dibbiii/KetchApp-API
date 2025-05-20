package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                dto.getUserUUID(),
                dto.getTomatoId(),
                dto.getType(),
                dto.getAction()
        );
    }

    public List<ActivityDto> getActivities() {
        List<ActivityEntity> activities = activitiesRepository.findAll();
        return activities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ActivityDto getActivity(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<ActivityEntity> activity = activitiesRepository.findById(id);
        return convertEntityToDto(activity.orElse(null));
    }

    public ActivityDto createActivity(ActivityDto activityDto) {
        if (activityDto == null || activityDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Activity data or user UUID cannot be null");
        }
        ActivityEntity activityEntity = convertDtoToEntity(activityDto);
        if (activityEntity == null) {
            throw new IllegalArgumentException("ActivityEntity cannot be null");
        }
        activityEntity = activitiesRepository.save(activityEntity);
        return convertEntityToDto(activityEntity);
    }

    public ActivityDto deleteActivity(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<ActivityEntity> activityOptional = activitiesRepository.findById(id);
        if (activityOptional.isPresent()) {
            activitiesRepository.delete(activityOptional.get());
            return convertEntityToDto(activityOptional.get());
        } else {
            throw new IllegalArgumentException("Activity with ID '" + id + "' not found.");
        }
    }
}