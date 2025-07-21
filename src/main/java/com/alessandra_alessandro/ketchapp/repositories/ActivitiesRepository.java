package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesRepository
    extends JpaRepository<ActivityEntity, Integer> {
    List<ActivityEntity> findByTomatoId(Integer tomatoId);
}
