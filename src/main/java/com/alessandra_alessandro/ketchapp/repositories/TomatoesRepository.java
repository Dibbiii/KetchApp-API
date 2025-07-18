package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.TomatoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TomatoesRepository extends JpaRepository<TomatoEntity, Integer> {
}