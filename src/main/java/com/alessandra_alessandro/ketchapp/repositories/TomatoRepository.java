package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TomatoRepository implements GenericRepository<TomatoEntity, Integer> {

    @Override
    public TomatoEntity create(TomatoEntity entity) {
        // TODO: Implement TomatoRepository.create
        return null;
    }

    @Override
    public TomatoEntity update(TomatoEntity entity) {
        // TODO: Implement TomatoRepository.update
        return null;
    }

    @Override
    public TomatoEntity delete(TomatoEntity entity) {
        // TODO: Implement TomatoRepository.delete
        return null;
    }

    @Override
    public boolean deleteByUUID(UUID uuid) {
        // TODO: Implement TomatoRepository.deleteByUUID
        return false;
    }

    @Override
    public Optional<TomatoEntity> selectById(Integer integer) {
        // TODO: Implement TomatoRepository.selectById
        return Optional.empty();
    }

    @Override
    public List<TomatoEntity> selectAll() {
        // TODO: Implement TomatoRepository.selectAll
        return List.of();
    }
}