package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.dto.TomatoDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import com.alessandra_alessandro.ketchapp.repositories.TomatoesRepository;
import com.alessandra_alessandro.ketchapp.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class TomatoesControllers {
    private static final Logger log = LoggerFactory.getLogger(TomatoesControllers.class);
    final TomatoesRepository tomatoesRepository;
    private final ActivitiesRepository activitiesRepository;
    private final EntityMapper entityMapper;

    @Autowired
    public TomatoesControllers(TomatoesRepository tomatoesRepository, ActivitiesRepository activitiesRepository, EntityMapper entityMapper) {
        this.tomatoesRepository = tomatoesRepository;
        this.activitiesRepository = activitiesRepository;
        this.entityMapper = entityMapper;
    }

    /**
     * Creates a new Tomato from a TomatoDto object.
     * Converts the DTO to an entity, saves it to the repository, and returns the DTO of the saved Tomato.
     *
     * @param tomatoDto the DTO of the Tomato to create
     * @return the DTO of the newly created and saved Tomato
     * @throws IllegalArgumentException if the tomatoDto parameter is null
     */
    public TomatoDto createTomato(TomatoDto tomatoDto) {
        log.info("Received TomatoDto for creation: {}", tomatoDto);
        if (tomatoDto == null) {
            log.error("TomatoDto cannot be null");
            throw new IllegalArgumentException("TomatoDto cannot be null");
        }
        TomatoEntity tomatoEntity = entityMapper.tomatoDtoToEntity(tomatoDto);
        log.info("Converted TomatoDto to TomatoEntity: {}", tomatoEntity);
        TomatoEntity savedTomato = tomatoesRepository.save(tomatoEntity);
        log.info("Saved TomatoEntity: {}", savedTomato);
        TomatoDto result = entityMapper.tomatoEntityToDto(savedTomato);
        log.info("Converted saved TomatoEntity to TomatoDto: {}", result);
        return result;
    }

    /**
     * Returns a list of ActivityDto associated with a specific tomato by its ID.
     *
     * @param tomatoId the ID of the tomato to retrieve activities for
     * @return list of ActivityDto linked to the specified tomato
     */
    public List<ActivityDto> getActivitiesByTomatoId(Integer tomatoId) {
        log.info("Fetching activities for tomatoId: {}", tomatoId);
        List<ActivityEntity> activities = activitiesRepository.findByTomatoId(tomatoId);
        log.info("Found {} activities for tomatoId {}", activities.size(), tomatoId);
        List<ActivityDto> result = activities.stream()
                .map(entityMapper::activityEntityToDto)
                .collect(java.util.stream.Collectors.toList());
        log.info("Mapped activities to ActivityDto list: {}", result);
        return result;
    }
}