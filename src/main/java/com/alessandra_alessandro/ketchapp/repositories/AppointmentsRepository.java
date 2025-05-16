package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentsRepository extends JpaRepository<AppointmentEntity, UUID> {
    // TODO: See if we need to add more methods here AppointmentsRepository
}
