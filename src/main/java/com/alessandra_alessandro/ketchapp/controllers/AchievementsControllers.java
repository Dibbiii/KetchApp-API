package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.AchievementDto;
import com.alessandra_alessandro.ketchapp.models.entity.AchievementEntity;
import com.alessandra_alessandro.ketchapp.repositories.AchievementsRepository;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchievementsControllers {
    private final AchievementsRepository achievementsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public AchievementsControllers(AchievementsRepository achievementsRepository, UsersRepository usersRepository) {
        this.achievementsRepository = achievementsRepository;
        this.usersRepository = usersRepository;
    }

    private AchievementDto convertEntityToDto(AchievementEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AchievementDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getDescription(),
                entity.getCompleted(),
                entity.getCreatedAt()
        );
    }

    private AchievementEntity convertDtoToEntity(AchievementDto dto) {
        if (dto == null) {
            return null;
        }
        // Use the constructor with icon
        return new AchievementEntity(dto.getUserUUID(), dto.getDescription(), dto.getCompleted(), dto.getIcon());
    }

    public List<AchievementDto> getAchievements() {
        List<AchievementEntity> entities = achievementsRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public AchievementDto createAchievement(AchievementDto achievementDto) {
        if (achievementDto == null || achievementDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Achievement data or user UUID cannot be null");
        }
        AchievementEntity achievementEntity = convertDtoToEntity(achievementDto);
        achievementEntity = achievementsRepository.save(achievementEntity);
        return convertEntityToDto(achievementEntity);
    }

    public AchievementDto deleteAchievement(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Optional<AchievementEntity> achievementOptional = achievementsRepository.findById(id);
        if (achievementOptional.isPresent()) {
            achievementsRepository.delete(achievementOptional.get());
            return convertEntityToDto(achievementOptional.get());
        } else {
            throw new IllegalArgumentException("Achievement with ID '" + id + "' not found.");
        }
    }
}