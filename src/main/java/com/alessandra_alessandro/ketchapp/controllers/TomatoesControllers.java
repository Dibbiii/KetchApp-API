package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.dto.TomatoDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import com.alessandra_alessandro.ketchapp.repositories.TomatoesRepository;
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

    @Autowired
    public TomatoesControllers(TomatoesRepository tomatoesRepository, ActivitiesRepository activitiesRepository) {
        this.tomatoesRepository = tomatoesRepository;
        this.activitiesRepository = activitiesRepository;
    }

    /**
     * Converts a TomatoEntity object to a TomatoDto object.
     *
     * @param entity the TomatoEntity to convert
     * @return the corresponding TomatoDto, or null if the entity is null
     */
    public TomatoDto convertEntityToDto(TomatoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new TomatoDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getStartAt(),
                entity.getEndAt(),
                entity.getPauseEnd(),
                entity.getNextTomatoId(),
                entity.getSubject(),
                entity.getCreatedAt()
        );
    }

    /**
     * Converts a TomatoDto object to a TomatoEntity object.
     *
     * @param dto the TomatoDto to convert
     * @return the corresponding TomatoEntity, or null if the dto is null
     */
    public TomatoEntity convertDtoToEntity(TomatoDto dto) {
        if (dto == null) {
            return null;
        }
        return new TomatoEntity(
                dto.getUserUUID(),
                dto.getStartAt(),
                dto.getEndAt(),
                dto.getPauseEnd(),
                dto.getNextTomatoId(),
                dto.getSubject()
        );
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
        log.debug("Received TomatoDto for creation: {}", tomatoDto);
        if (tomatoDto == null) {
            log.error("TomatoDto cannot be null");
            throw new IllegalArgumentException("TomatoDto cannot be null");
        }
        TomatoEntity tomatoEntity = convertDtoToEntity(tomatoDto);
        log.debug("Converted TomatoDto to TomatoEntity: {}", tomatoEntity);
        TomatoEntity savedTomato = tomatoesRepository.save(tomatoEntity);
        log.debug("Saved TomatoEntity: {}", savedTomato);
        TomatoDto result = convertEntityToDto(savedTomato);
        log.debug("Converted saved TomatoEntity to TomatoDto: {}", result);
        return result;
    }

    /**
     * Returns a list of ActivityDto associated with a specific tomato by its ID.
     *
     * @param tomatoId the ID of the tomato to retrieve activities for
     * @return list of ActivityDto linked to the specified tomato
     */
    public List<ActivityDto> getActivitiesByTomatoId(Integer tomatoId) {
        log.debug("Fetching activities for tomatoId: {}", tomatoId);
        List<ActivityEntity> activities = activitiesRepository.findByTomatoId(tomatoId);
        log.debug("Found {} activities for tomatoId {}", activities.size(), tomatoId);
        List<ActivityDto> result = activities.stream()
                .map(a -> new ActivityDto(
                        a.getId(),
                        a.getUserUUID(),
                        a.getTomatoId(),
                        a.getType(),
                        a.getAction(),
                        a.getCreatedAt()
                ))
                .collect(java.util.stream.Collectors.toList());
        log.debug("Mapped activities to ActivityDto list: {}", result);
        return result;
    }
}