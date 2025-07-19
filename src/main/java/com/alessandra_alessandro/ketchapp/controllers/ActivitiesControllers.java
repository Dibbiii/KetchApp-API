package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ActivitiesControllers {
    private static final Logger log = LoggerFactory.getLogger(ActivitiesControllers.class);
    private final ActivitiesRepository activitiesRepository;

    @Autowired
    public ActivitiesControllers(ActivitiesRepository activitiesRepository) {
        this.activitiesRepository = activitiesRepository;
    }

    /**
     * Converts an ActivityEntity object to an ActivityDto object.
     *
     * @param entity the ActivityEntity object to convert
     * @return the resulting ActivityDto, or null if the entity is null
     */
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

    /**
     * Converts an ActivityDto object to an ActivityEntity object.
     *
     * @param dto the ActivityDto object to convert
     * @return the resulting ActivityEntity, or null if the dto is null
     */
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

    /**
     * Creates a new activity from an ActivityDto object.
     * Validates that the object and the userUUID field are not null,
     * converts the DTO to an entity, saves it in the repository, and returns the resulting DTO.
     *
     * @param activityDto the ActivityDto object to create
     * @return the created and saved ActivityDto object
     * @throws IllegalArgumentException if activityDto or userUUID are null
     */
    public ActivityDto createActivity(ActivityDto activityDto) {
        log.debug("Received ActivityDto for creation: {}", activityDto);
        if (activityDto == null || activityDto.getUserUUID() == null) {
            log.error("Activity data or user UUID cannot be null");
            throw new IllegalArgumentException("Activity data or user UUID cannot be null");
        }
        log.debug("Converting ActivityDto to ActivityEntity");
        ActivityEntity activityEntity = convertDtoToEntity(activityDto);
        log.debug("Saving ActivityEntity to repository: {}", activityEntity);
        activityEntity = activitiesRepository.save(activityEntity);
        log.debug("Saved ActivityEntity: {}", activityEntity);
        ActivityDto result = convertEntityToDto(activityEntity);
        log.debug("Converted saved ActivityEntity to ActivityDto: {}", result);
        return result;
    }
}