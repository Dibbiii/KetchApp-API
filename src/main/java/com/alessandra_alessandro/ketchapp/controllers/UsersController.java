package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private UserDto convertEntityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getUuid(),
                entity.getUsername(),
                entity.getCreatedAt()
        );
    }

    private UserEntity convertDtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        return new UserEntity(dto.getUsername());
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto == null || userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User data or username cannot be null or empty");
        }
        Optional<UserEntity> existingUser = usersRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Username '" + userDto.getUsername() + "' already exists.");
        }

        UserEntity entityToSave = convertDtoToEntity(userDto);
        UserEntity savedEntity = usersRepository.save(entityToSave);
        return convertEntityToDto(savedEntity);
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> entities = usersRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(uuid);
        return entityOptional.map(this::convertEntityToDto).orElse(null);
    }

    @Transactional
    public boolean deleteUserByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null for deletion");
        }
        if (usersRepository.existsById(uuid)) {
            usersRepository.deleteById(uuid);
            return true;
        }
        return false;
    }
}