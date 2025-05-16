package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TomatoesRepository extends JpaRepository<TomatoEntity, UUID> {
    // TODO: See if we need to add more methods here TomatoesRepository
}