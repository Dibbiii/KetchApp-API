package com.alessandra_alessandro.ketchapp.repositories;

import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.utils.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsersRepository implements GenericRepository<UserEntity, UUID>{
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> records = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT uuid, created_at FROM users";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            while (res.next()) {
                UUID uuid = UUID.fromString(res.getString("uuid"));
                Timestamp createdAt = res.getTimestamp("created_at");
                records.add(new UserEntity(uuid, createdAt));
            }

            res.close();
            stmt.close();
        }
        return records;
    }

    public UserEntity findById(UUID uuid) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE uuid = '" + uuid + "'";
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            if(res.next()) {
                return new UserEntity(
                        UUID.fromString(res.getString("uuid")),
                        res.getTimestamp("created_at")
                );
            }
            return null;
        }
    }
    //* Create a new user
    //* Delete a user
    //* Update a user
    //* Find a user by UUID

    public UserEntity createUser(String username) throws SQLException { //uso username perchè è un campo obbligatorio -> mi serve per creare una nnuova riga nel DB
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO users (username) VALUES (?) RETURNING uuid, created_at";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                UUID uuid = UUID.fromString(res.getString("uuid"));
                Timestamp createdAt = res.getTimestamp("created_at");
                return new UserEntity(uuid, createdAt);
            }
            return null;
        }
    }

    @Override
    public UserEntity create(UserEntity entity) {
        // TODO: Implement UserRepository.create
        return null;
    }

    @Override
    public UserEntity update(UserEntity entity) {
        // TODO: Implement UserRepository.update
        return null;
    }

    @Override
    public UserEntity delete(UserEntity entity) {
        // TODO: Implement UserRepository.delete
        return null;
    }

    @Override
    public boolean deleteByUUID(UUID uuid) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE uuid = ? RETURNING uuid";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setObject(1, uuid);
                try (ResultSet res = stmt.executeQuery()) {
                    return res.next();
                }
            }
        }
    }

    @Override
    public Optional<UserEntity> selectById(UUID uuid) {
        // TODO: Implement UserRepository.selectById
        return Optional.empty();
    }

    @Override
    public List<UserEntity> selectAll() {
        // TODO: Implement UserRepository.selectAll
        return List.of();
    }
}