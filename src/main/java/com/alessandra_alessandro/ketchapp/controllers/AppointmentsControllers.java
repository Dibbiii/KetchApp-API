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
public class AppointmentsControllers {

    private final AppointmentsRepository appointmentsRepository;

    @Autowired
    public AppointmentsControllers(AppointmentsRepository appointmentsRepository) {
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
                dto.getUserUUID(),
                dto.getName(),
                dto.getStartAt(),
                dto.getEndAt()
        );
    }

    public List<AppointmentDto> getAppointments() {
        List<AppointmentEntity> entities = appointmentsRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    public AppointmentDto getAppointment(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        Optional<AppointmentEntity> entity = appointmentsRepository.findById(id);
        if (entity.isPresent()) {
            return convertEntityToDto(entity.get());
        } else {
            throw new IllegalArgumentException("Appointment with ID '" + id + "' not found.");
        }
    }

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto appointmentDto) {
        if (appointmentDto == null || appointmentDto.getUserUUID() == null) {
            throw new IllegalArgumentException("Appointment data or user UUID cannot be null");
        }
        AppointmentEntity appointmentEntity = convertDtoToEntity(appointmentDto);
        if (appointmentEntity == null) {
            throw new IllegalArgumentException("AppointmentEntity cannot be null");
        }
        appointmentEntity = appointmentsRepository.save(appointmentEntity);
        return convertEntityToDto(appointmentEntity);
    }

    @Transactional
    public AppointmentDto deleteAppointment(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Appointment ID cannot be null");
        }
        Optional<AppointmentEntity> entityOptional = appointmentsRepository.findById(id);
        if (entityOptional.isPresent()) {
            appointmentsRepository.delete(entityOptional.get());
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("Appointment with ID '" + id + "' not found.");
        }
    }
}

