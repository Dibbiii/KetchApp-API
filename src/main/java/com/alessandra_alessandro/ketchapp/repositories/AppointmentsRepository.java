package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentsRepository extends JpaRepository<AppointmentEntity, Integer> {
    Optional<AppointmentEntity> findByName(String name);
}