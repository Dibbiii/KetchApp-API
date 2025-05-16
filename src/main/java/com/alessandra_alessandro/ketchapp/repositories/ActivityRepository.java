package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.ActivityEntity;

import java.util.List;
import java.util.Optional;

public class ActivityRepository implements GenericRepository<ActivityEntity, Integer> {

    @Override
    public ActivityEntity create(ActivityEntity entity) {
        // TODO: Implement ActivityRepository.create
        return null;
    }

    @Override
    public ActivityEntity update(ActivityEntity entity) {
        // TODO: Implement ActivityRepository.update
        return null;
    }

    @Override
    public ActivityEntity delete(ActivityEntity entity) {
        // TODO: Implement ActivityRepository.delete
        return null;
    }

    @Override
    public Optional<ActivityEntity> selectById(Integer integer) {
        // TODO: Implement ActivityRepository.selectById
        return Optional.empty();
    }

    @Override
    public List<ActivityEntity> selectAll() {
        // TODO: Implement ActivityRepository.selectAll
        return List.of();
    }
}