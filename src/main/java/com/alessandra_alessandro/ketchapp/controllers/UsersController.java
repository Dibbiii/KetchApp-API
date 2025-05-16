package com.alessandra_alessandro.ketchapp.controllers;

import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UserDto> getAllUsers() throws SQLException {
        List<UserEntity> entities = usersRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (UserEntity entity : entities) {
            dtos.add(new UserDto(
                entity.getUuid(),
                entity.getCreated_at()
            ));
        }
        return dtos;
    }

    public UserDto getUserById(UUID uuid) throws SQLException {
        UserEntity user = usersRepository.findById(uuid);
        if (user != null) {
            return new UserDto(user.getUuid(), user.getCreated_at());
        }
        return null;
    }

    public UserDto createUser(String username) throws SQLException {
        UserEntity entity = usersRepository.createUser(username);
        if (entity != null) {
            return new UserDto(entity.getUuid(), entity.getCreated_at());
        }
        return null;
    }


    public boolean deleteByUUID(UUID uuid) throws SQLException {
        try {
            return usersRepository.deleteByUUID(uuid);
        } catch (SQLException e) {
            throw new SQLException("Error deleting user with UUID: " + uuid, e);
        }
    }
}