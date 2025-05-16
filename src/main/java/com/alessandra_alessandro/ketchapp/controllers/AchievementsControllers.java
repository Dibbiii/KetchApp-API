package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
import com.alessandra_alessandro.ketchapp.models.entity.AchievementEntity;
import com.alessandra_alessandro.ketchapp.repositories.AchievementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AchievementsControllers {
    private final AchievementsRepository achievementsRepository;

    @Autowired
    public AchievementsControllers(AchievementsRepository achievementsRepository) {
        this.achievementsRepository = achievementsRepository;
    }

    private AchievementDto convertEntityToDto(AchievementEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AchievementDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getAchievementNumber(),
                entity.getCreatedAt()
        );
    }

    private AchievementEntity convertDtoToEntity(AchievementDto dto) {
        if (dto == null) {
            return null;
        }
        return new AchievementEntity(dto.getUserUUID(), dto.getAchievementNumber());
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

    public AchievementDto getAchievementById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<AchievementEntity> entityOptional = achievementsRepository.findById(id);
        return entityOptional.map(this::convertEntityToDto).orElse(null);
    }

    public AchievementDto createAchievement(AchievementDto achievementDto) {
        if (achievementDto == null || achievementDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Achievement data or user UUID cannot be null");
        }
        AchievementEntity entityToSave = convertDtoToEntity(achievementDto);
        AchievementEntity savedEntity = achievementsRepository.save(entityToSave);
        return convertEntityToDto(savedEntity);
    }

    public void deleteAchievementById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        achievementsRepository.deleteById(id);
    }
}
