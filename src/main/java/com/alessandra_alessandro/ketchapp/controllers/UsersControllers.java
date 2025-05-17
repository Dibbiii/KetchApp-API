package com.alessandra_alessandro.ketchapp.controllers;

// Aggiungi le dipendenze del Firebase Admin SDK nel build.gradle:
// implementation 'com.google.firebase:firebase-admin:9.2.0'

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
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
public class UsersControllers {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersControllers(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    private UserDto convertEntityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getUuid(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getFirebaseUid(),
                entity.getCreatedAt()
        );
    }

    private UserEntity convertDtoToEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        // L'ordine giusto è: firebaseUid, email, username
        return new UserEntity(
        dto.getFirebaseUid(),   // firebaseUid
        dto.getEmail(),         // email
        dto.getUsername()       // username
    );
}

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("UserDto cannot be null");
        }
        UserEntity userEntity = convertDtoToEntity(userDto);
        if (userEntity == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        userEntity = usersRepository.save(userEntity);
        return convertEntityToDto(userEntity);
    }

    @Transactional
    public UserDto deleteUser(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(uuid);
        if (entityOptional.isPresent()) {
            usersRepository.delete(entityOptional.get());
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("User with UUID '" + uuid + "' not found.");
        }
    }

    public List<UserDto> getUsers() {
        List<UserEntity> entities = usersRepository.findAll();
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto getUser(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("UUID cannot be null");
        }
        Optional<UserEntity> entityOptional = usersRepository.findById(id);
        if (entityOptional.isPresent()) {
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("User with UUID '" + id + "' not found.");
        }
    }

    // Restituisce la mail dato l'username
    public UserDto getEmailByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        Optional<UserEntity> entityOptional = usersRepository.findByUsername(username);
        if (entityOptional.isPresent()) {
            return convertEntityToDto(entityOptional.get());
        } else {
            throw new IllegalArgumentException("User with username '" + username + "' not found.");
        }
    }

    public UserDto loginWithGoogleToken(String idTokenString) {
        try {
            // Verifica il token Google/Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idTokenString);
            String firebaseUid = decodedToken.getUid();
            String email = decodedToken.getEmail();
            String username = decodedToken.getName(); // oppure ricavarla come preferisci

            Optional<UserEntity> entityOptional = usersRepository.findByFirebaseUid(firebaseUid);

            UserEntity userEntity;
            // Se l'utente esiste già, aggiornalo se necessario
            if (entityOptional.isPresent()) {
                userEntity = entityOptional.get();
                userEntity.setEmail(email);
                userEntity.setUsername(username);
            } else {
                // Altrimenti creane uno nuovo
                userEntity = new UserEntity(firebaseUid, email, username);
            }
            usersRepository.save(userEntity);
            return convertEntityToDto(userEntity);
        } catch (Exception e) {
            throw new RuntimeException("Token Google non valido", e);
        }
    }
}