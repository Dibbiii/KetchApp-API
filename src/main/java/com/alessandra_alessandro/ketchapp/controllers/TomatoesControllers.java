package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.ActivityDto;
import com.alessandra_alessandro.ketchapp.models.dto.TomatoDto;
import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import com.alessandra_alessandro.ketchapp.repositories.ActivitiesRepository;
import com.alessandra_alessandro.ketchapp.repositories.TomatoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TomatoesControllers {
    final TomatoesRepository tomatoesRepository;
    private final ActivitiesRepository activitiesRepository;

    @Autowired
    public TomatoesControllers(TomatoesRepository tomatoesRepository, ActivitiesRepository activitiesRepository) {
        this.tomatoesRepository = tomatoesRepository;
        this.activitiesRepository = activitiesRepository;
    }

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

    public List<TomatoDto> getTomatoes() {
        List<TomatoEntity> tomatoes = tomatoesRepository.findAll();
        return tomatoes.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public TomatoDto getTomato(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<TomatoEntity> tomatoEntity = tomatoesRepository.findById(id);
        if (tomatoEntity.isPresent()) {
            return convertEntityToDto(tomatoEntity.get());
        } else {
            throw new IllegalArgumentException("Tomato not found");
        }
    }

    public TomatoDto createTomato(TomatoDto tomatoDto) {
        if (tomatoDto == null) {
            throw new IllegalArgumentException("TomatoDto cannot be null");
        }
        TomatoEntity tomatoEntity = convertDtoToEntity(tomatoDto);
        TomatoEntity savedTomato = tomatoesRepository.save(tomatoEntity);
        return convertEntityToDto(savedTomato);
    }

    public TomatoDto deleteTomato(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<TomatoEntity> tomatoEntity = tomatoesRepository.findById(id);
        if (tomatoEntity.isPresent()) {
            tomatoesRepository.delete(tomatoEntity.get());
            return convertEntityToDto(tomatoEntity.get());
        } else {
            throw new IllegalArgumentException("Tomato not found");
        }
    }

    public List<ActivityDto> getActivitiesByTomatoId(Integer tomatoId) {
        List<ActivityEntity> activities = activitiesRepository.findByTomatoId(tomatoId);
        return activities.stream()
                .map(a -> new ActivityDto(
                        a.getId(),
                        a.getUserUUID(),
                        a.getTomatoId(),
                        a.getType(),
                        a.getAction(),
                        a.getCreatedAt()
                ))
                .collect(java.util.stream.Collectors.toList());
    }
}