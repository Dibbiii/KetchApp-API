package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importante per operazioni che modificano dati

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service // Già presente, ottimo!
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    // Metodo helper per convertire Entity a DTO
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

    // Metodo helper per convertire DTO a Entity (per la creazione)
    // Potrebbe non essere necessario se il costruttore di UserEntity è sufficiente
    private UserEntity convertDtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        // Usiamo il costruttore di UserEntity che imposta UUID e createdAt
        return new UserEntity(dto.getUsername());
    }

    @Transactional // Buona pratica per metodi che modificano dati
    public UserDto createUser(UserDto userDto) {
        if (userDto == null || userDto.getUsername() == null || userDto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("User data or username cannot be null or empty");
        }
        // Verifica se l'utente esiste già (opzionale, dipende dalla logica di business)
        Optional<UserEntity> existingUser = usersRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            // Puoi decidere di lanciare un'eccezione specifica,
            // ad esempio DataIntegrityViolationException o una custom
            throw new IllegalArgumentException("Username '" + userDto.getUsername() + "' already exists.");
        }

        UserEntity entityToSave = convertDtoToEntity(userDto);
        // L'UUID e createdAt vengono impostati nel costruttore di UserEntity(String username)
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
        return entityOptional.map(this::convertEntityToDto).orElse(null); // Restituisce null se non trovato, la rotta gestirà il 404
    }

    @Transactional // Buona pratica per metodi che modificano dati
    public boolean deleteUserByUUID(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null for deletion");
        }
        if (usersRepository.existsById(uuid)) {
            usersRepository.deleteById(uuid);
            return true;
        }
        return false; // Utente non trovato, non è stato cancellato
    }
}