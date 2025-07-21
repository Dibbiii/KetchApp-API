package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.AchievementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementsRepository
    extends JpaRepository<AchievementEntity, Integer> {}
