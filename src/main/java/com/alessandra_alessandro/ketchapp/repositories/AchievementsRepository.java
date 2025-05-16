package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AchievementsRepository extends JpaRepository<AchievementEntity, UUID> {
    // TODO: See if we need to add more methods here AchievementsRepository
}
