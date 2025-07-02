package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivitiesRepository extends JpaRepository<ActivityEntity, Integer> {
    List<ActivityEntity> findByTomatoId(Integer tomatoId);
}
