package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
import com.alessandra_alessandro.ketchapp.models.entity.AchievementEntity;
import com.alessandra_alessandro.ketchapp.repositories.AchievementsRepository;
import com.alessandra_alessandro.ketchapp.routes.AchievementRoute;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementsController {
    private final AchievementRoute achievementRoute;
    private final AchievementsRepository achievementsRepository;

    @Autowired
    public AchievementsController(AchievementRoute achievementRoute, AchievementsRepository achievementsRepository) {
        this.achievementRoute = achievementRoute;
        this.achievementsRepository = achievementsRepository;
    }

    private AchievementDto convertEntityToDto(AchievementEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AchievementDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getCreatedAt()
        );
    }

    private AchievementDto convertDtoToEntity(AchievementDto dto) {
        if (dto == null) {
            return null;
        }
        return new AchievementDto(dto.getId(), dto.getUserUUID(), dto.getCreatedAt());
    }

    public List<AchievementDto> getAllAchievements() {
        List<AchievementEntity> entities = achievementsRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public AchievementDto getAchievementByUserUUID(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<AchievementEntity> entityOptional = achievementsRepository.findByUserUUID(uuid);
        return entityOptional.map(this::convertEntityToDto).orElse(null);
    }
}
