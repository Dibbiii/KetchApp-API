package com.alessandra_alessandro.ketchapp.repository;

import com.alessandra_alessandro.ketchapp.models.entity.UserEntity;
import com.alessandra_alessandro.ketchapp.utils.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class UsersRepository {
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
}