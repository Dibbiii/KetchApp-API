package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.AppointmentDto;
import com.alessandra_alessandro.ketchapp.models.entity.AppointmentEntity;
import com.alessandra_alessandro.ketchapp.repositories.AppointmentsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppointmentsController {

    private final AppointmentsRepository appointmentsRepository;

    @Autowired
    public AppointmentsController(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    private AppointmentDto convertEntityToDto(AppointmentEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AppointmentDto(
                entity.getId(),
                entity.getUserUUID(),
                entity.getCreatedAt(),
                entity.getName(),
                entity.getStartAt(),
                entity.getEndAt()
        );
    }

    private AppointmentEntity convertDtoToEntity(AppointmentDto dto) {
        if (dto == null) {
            return null;
        }
        return new AppointmentEntity(
                dto.getId(),
                dto.getUserUUID(),
                dto.getCreatedAt(),
                dto.getName(),
                dto.getStartAt(),
                dto.getEndAt()
        );
    }
    public List<AppointmentDto> getAllAppointments(UUID userUUID) {
        if(userUUID == null) {
            throw new IllegalArgumentException("User UUID cannot be null");
        }
        List<AppointmentEntity> entities = appointmentsRepository.findByUserUUID(userUUID);
        return entities.stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        if (appointmentDto == null || appointmentDto.getName() == null || appointmentDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Appointment data or name cannot be null or empty");
        }
        Optional<AppointmentEntity> existingAppointment = appointmentsRepository.findByName(appointmentDto.getName());
        if (existingAppointment.isPresent()) {
            throw new IllegalArgumentException("Appointment with name '" + appointmentDto.getName() + "' already exists.");
        }

        AppointmentEntity entityToSave = convertDtoToEntity(appointmentDto);
        AppointmentEntity savedEntity = appointmentsRepository.save(entityToSave);
        return convertEntityToDto(savedEntity);

    }


}

