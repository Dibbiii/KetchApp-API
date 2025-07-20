package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import com.alessandra_alessandro.ketchapp.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ActivitiesControllers {
    private static final Logger log = LoggerFactory.getLogger(ActivitiesControllers.class);
    private final ActivitiesRepository activitiesRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public ActivitiesControllers(ActivitiesRepository activitiesRepository, EntityMapper entityMapper) {
        this.activitiesRepository = activitiesRepository;
        this.entityMapper = entityMapper;
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
        log.info("Received ActivityDto for creation: {}", activityDto);
        if (activityDto == null || activityDto.getUserUUID() == null) {
            log.error("Activity data or user UUID cannot be null");
            throw new IllegalArgumentException("Activity data or user UUID cannot be null");
        }
        log.info("Converting ActivityDto to ActivityEntity");
        ActivityEntity activityEntity = entityMapper.activityDtoToEntity(activityDto);
        log.info("Saving ActivityEntity to repository: {}", activityEntity);
        activityEntity = activitiesRepository.save(activityEntity);
        log.info("Saved ActivityEntity: {}", activityEntity);
        ActivityDto result = entityMapper.activityEntityToDto(activityEntity);
        log.info("Converted saved ActivityEntity to ActivityDto: {}", result);
        return result;
    }
}