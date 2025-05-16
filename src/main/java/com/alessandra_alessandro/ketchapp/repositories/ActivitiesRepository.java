package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ActivitiesRepository extends JpaRepository<ActivityEntity, Integer> {
    Optional<ActivityEntity> findByTomatoId(Integer tomatoId);
    // TODO: See if we need to add more methods here ActivitiesRepository
}
