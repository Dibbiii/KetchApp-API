package com.alessandra_alessandro.ketchapp.controller;

import com.alessandra_alessandro.ketchapp.models.dto.UserDto;
import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}